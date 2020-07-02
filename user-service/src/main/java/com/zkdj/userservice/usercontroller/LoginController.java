package com.zkdj.userservice.usercontroller;

import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.service.UserService;
import com.zkdj.userservice.util.CheckUtil;
import com.zkdj.userservice.util.ResultObject;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param users
     * @return
     */
    @PostMapping("/userLogin")
    public ResultObject userLogin(Users users) {
        ResultObject<Map> result = new ResultObject();
        if(StringUtils.isEmpty(users.getUserName()) || StringUtils.isEmpty(users.getUserPwd())){
            return new ResultObject(400,"用户信息用户名或密码不能为空",null);
        }
        try {
            Users userLogin = this.userService.userLogin(users);
            Map<String, Object> data = new HashMap<>();
                System.out.println(userLogin+"------");
            if (userLogin != null) {
                //数据库有用户信息记录
                //1 是启用  2 是禁用
                if (!StringUtils.isEmpty(userLogin.getUserState()) && userLogin.getUserState() != 1 ) {
                    result.setCode(400);
                     result.setMessage("当前账号被禁用！");
                    return result;
                }
                String token = UUID.randomUUID().toString().replace("-","").toUpperCase().substring(0,16);
                //登录成功  将用户的信息存入redis
                data.put("userId", userLogin.getUserId());
                data.put("userName", userLogin.getUserName());
                userService.stockIntoRedis(userLogin);
                //将token存入redis中
                int i=userService.addTokenToRedis(token, data);
                data.clear();
                data.put("token", token);
                data.put("userLogin", userLogin);
                System.out.println(data+"------");
                result.setResultData(data);
                result.setCode(200);
                result.setMessage("登录成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //数据库不存在用户的信息
        result.setCode(400);
        result.setMessage("登录失败！用户名或密码错误");
        return result;
    }


    //    更新用户信息
    @PostMapping("/updateUserMessage")
    public ResultObject updateUserMessage(Users users) {
        ResultObject result = new ResultObject();
        try {
            int i = userService.updateUserMessage(users);
            if (i > 0) {
                result.setCode(200);
                result.setMessage("用户信息更新成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("用户信息更新失败，请与管理员联系");
        return result;
    }


    //用户查看自己的信息
    @PostMapping("/selectUserMessage")
    public Users selectUserMessage(Integer userId) {
        System.out.println("查询用户信息");
        Users users = userService.selectUserMessage(userId);
        return users;
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public ResultObject logout(@RequestParam("user_id") Integer userId){
        try {
            System.out.println("注销用户");

            if(!CheckUtil.checkIntNum(userId)){
                return new ResultObject(400,"服务器繁,请稍候再试",null);
            }
            //修改redis里面的数据
            //先看看有没有该用户
            Users users=userService.selectUserMessage(userId);
            if(users!=null){
                System.out.println("注销用户---");
                //去redis里面将用户的信息给删除了
                Integer i=userService.deleteLoginUserFromRedis(userId);
                if(i>0){
                    System.out.println("注销用户---2");
                    return new ResultObject(200,"退出成功",null);
                }
                return new ResultObject(400,"退出时发生异常",null);
            }
            System.out.println("注销用户  end");
            return new ResultObject(400,"退出失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
