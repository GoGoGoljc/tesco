package com.zkdj.userservice.usercontroller;

import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.sun.org.apache.xpath.internal.operations.Mult;
import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.domain.Verify;
import com.zkdj.userservice.service.UserService;
import com.zkdj.userservice.util.CheckUtil;
import com.zkdj.userservice.util.FileUtil;
import com.zkdj.userservice.util.ResultObject;
import io.netty.util.internal.StringUtil;
import javafx.beans.binding.ObjectBinding;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户id查找用户的个人信息 (原来是get  现在变成post)
     */
    @PostMapping("/findUserInfoById")
    public ResultObject findUserInfoById(@RequestParam("user_id") Integer userId){
        try {
            if(!CheckUtil.checkIntNum(userId)){
                return new ResultObject(400,"服务器繁忙,请重试",null);
            }

            Users user=userService.selectUserMessage(userId);
            if(user!=null){
                return  new ResultObject(200,"查询成功",user);
            }
            return   new ResultObject(400,"用户不存在",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据用户查询用户
     */
    @GetMapping("/findUserByName")
    public ResultObject findUserByName(@RequestParam("userName") String userName){
        try {
            if(StringUtils.isEmpty(userName)){
                return new ResultObject(400,"服务器繁忙,请重试",null);
            }
            Users user= userService.userNameExist(userName);
            if(user!=null){
                return new ResultObject(200,"查询成功",user);
            }
            return new ResultObject(400,"查询失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 还要校验用户信息
     * 根据条件模糊查询用户
     * 用户名  电话
     */

    @PostMapping("/findUserByCondition")
    public ResultObject findUserByCondition(Users users){
        //如果条件为空 那么就查询全部了
        System.out.println(users+"PPPPPPPPPPPPPPPP");
        try {
            List<Users> usersList= userService.findUserByCondition(users);
            if(usersList!=null && usersList.size()>0){
                return new ResultObject(200,"查询成功",usersList);
            }
            return new ResultObject(400,"没有查询结果",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }


    //修改用户信息
    @PostMapping("/updateUserInfoSelective")
    public ResultObject updateUserInfoSelective(Users users){
        try {
            //检验用户信息
            Integer i=userService.updateUserMessage(users);
            if(i>0){
                return new ResultObject(200,"修改成功",null);
            }
            return new ResultObject(200,"修改失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
     * 分页查询所有用户信息
     */
    @PostMapping("/findUsersByPage")
    public ResultObject findUsersByPage(@RequestParam("pageNum") Integer pageNum){
        System.out.println("分页查询用户");
        try {
          if(!CheckUtil.checkIntNum(pageNum)){
              return new ResultObject(400,"服务器繁,请稍候再试",null);
          }
            PageInfo<Users> pageUsers= userService.findUsersByPage(pageNum);
            List<Users> list = pageUsers.getList();
            if(list!=null && list.size()>0){
                return  new ResultObject(200,"查询成功",pageUsers);
            }
            return  new ResultObject(400,"查询失败",pageUsers);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/findAll")
    public ResultObject findAll(){
        try {
            List<Users> usersList=userService.findAll();
            if(usersList!=null && usersList.size()>0){
                return new ResultObject(200,"成功查询所有用户",usersList);
            }
            return new ResultObject(200,"查询所有用户失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据用户id 找回（修改）密码  或者直接使用根据用户id动态修改用户信息
     */
    @GetMapping("/findPasswordById")
    public ResultObject findPasswordById(@RequestParam("userId") Integer userId,@RequestParam("userPwd") String password){
        try {
            if(!CheckUtil.checkIntNum(userId)){
                return new ResultObject(400,"服务器繁忙,请稍候再试",null);
            }

            if(StringUtils.isEmpty(password)){
                return new ResultObject(400,"服务器繁忙,请稍候再试",null);
            }
            //根据id查询用户信息
            Users  users= userService.selectUserMessage(userId);
            if(users!=null){
               //有该用户  修改用户的密码
                Users users1 = new Users();
                users1.setUserId(userId);
                users1.setUserPwd(password);
                Integer i=userService.updateUserMessage(users1);
                if(i>0){
                    return new ResultObject(200,"修改密码成功",null);
                }
            }
            return new ResultObject(400,"修改失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 修改用户头像
     */
    @PostMapping("/updateUserImage")
    public  ResultObject updateUserImage(@RequestParam("userImage") MultipartFile multipartFile,@RequestParam("userId") Integer userId){
if(!CheckUtil.checkIntNum(userId)){
    return new ResultObject(400,"服务器繁忙,请稍候再试",null);
}
//如何校验文件
/*
if(multipartFile.getSize()<=0){
    return new ResultObject(400,"服务器繁忙,请稍候再试",null);
}
*/
        try {
            String filePath="F:\\user-image\\";
            //获取到图片的新的名称
            String fileName= FileUtil.getFileName();
            //获取图片的后缀名称
            String extension=FileUtil.getExtension(multipartFile.getOriginalFilename());

            File file=new File(filePath+fileName+extension);
            if(!file.exists()){
                file.mkdirs();
            }
            multipartFile.transferTo(file);
            //顺便把用户数据库里面的数据给修改了
            Users users = new Users();
            users.setUserId(userId);
            //这里的文件路径需要改一下
            users.setUserImage(filePath+fileName+extension);
            userService.updateUserMessage(users);
            Map<String,String> map=new HashMap<>();
            map.put("fileName",fileName);
            map.put("filePath",filePath);
            map.put("extension",extension);
            return new ResultObject(400,"修改成功",map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传失败
        return new ResultObject(400,"修改失败",null);
    }



    /**
     * 根据验证码修改密码
     */

   /* @PostMapping("/updatePasswordByEmail")
    public ResultObject updatePasswordByEmail(@RequestParam("userEmail") String userEmail,
                                              @RequestParam("userPwd") String userPwd,
                                              @RequestParam("code") String code){*/
    @PostMapping("/updatePasswordByEmail")
    public ResultObject updatePasswordByEmail(Verify verify){
        try {
            if (!CheckUtil.checkIntNum(verify.getUserId())){
                return new ResultObject(400,"服务器繁忙，请稍后再试",null);
            }
            if (StringUtils.isEmpty(verify.getCode())){
                return new ResultObject(400,"验证码不能空",null);
            }
            if (StringUtils.isEmpty(verify.getUserEmail())){
                return new ResultObject(400,"用户邮箱不能为空",null);
            }
            if (StringUtils.isEmpty(verify.getUserPwd())){
                return new ResultObject(400,"新密码不能为空",null);
            }
           //校验验证码是否正确
            Integer m=userService.verifyCode(verify);
            System.out.println("''========="+m);
            if(m<1){
                //验证码不正确
                return new ResultObject(400,"验证码不正确",null);
            }
            Integer i=userService.updatePasswordByEmail(verify);
            if(i>0){
                return new ResultObject(200,"修改密码成功",null);
            }
            return new ResultObject(200,"修改密码失败,请检查一下信息",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据邮箱修改密码
     */
    @PostMapping("/updatePwdByEmail")
    public ResultObject updatePwdByEmail(@RequestParam("email") String email,
                                         @RequestParam("password")String password){
        try {
            Integer i=userService.updatePwdByEmail(email,password);
            if(i>0){
                return new ResultObject(200,"修改密码成功",null);
            }
            return new ResultObject(200,"修改密码失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据用户id删除用户
     */
     @PostMapping("/deleteUserById")
    public ResultObject deleteUserById(@RequestParam("userId") Integer userId){
        try {
            if(StringUtils.isEmpty(userId+"")){
                return new ResultObject(400,"服务器发生错误",null);
            }

            Integer i=userService.deleteUserById(userId);
            if(i>0){
                return new ResultObject(200,"删除用户成功",null);
            }
            return new ResultObject(200,"删除用户失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 添加用户
     */
    @PostMapping("/addUser")
    public ResultObject addUser(Users users){
        try {
            users.setUserPwd("123456");
            Integer userRegister = userService.userRegister(users);
            if (userRegister > 0){
               return new ResultObject(200,"添加成功",null);
            }else{
              return new ResultObject(400,"添加失败,用户名已存在",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
