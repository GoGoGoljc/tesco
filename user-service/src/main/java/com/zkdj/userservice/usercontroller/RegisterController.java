package com.zkdj.userservice.usercontroller;

import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.service.UserService;
import com.zkdj.userservice.util.EmailUtil;
import com.zkdj.userservice.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping("/userRegister" )
    public Object userRegister(Users users,@RequestParam("code") String code) {
        try {
           if(StringUtils.isEmpty(code)){
               return new ResultObject(400,"验证码不能为空",null);
           }
            if(StringUtils.isEmpty(users.getUserEmail())){
                return new ResultObject(400,"邮箱不能空",null);
            }
            ResultObject result = new ResultObject();
          //前台进行校验 确保不出错
            //可以对用户注册的信息进行检验
//        注册用户的信息
//验证验证是否对不对
            System.out.println(users+"======="+code);
            String verifyCode=userService.getCode(users.getUserEmail());
            System.out.println("从redis中获取的验证码是"+verifyCode);
            if(!StringUtils.isEmpty(verifyCode)){
                //验证码不为空
                if(!verifyCode.equals(code)){
                    return new ResultObject(400,"验证码不正确",null);
                }
            }else{
                return new ResultObject(400,"验证码已失效,请重新获取验证码",null);
            }
            Integer userRegister = userService.userRegister(users);
            if (userRegister > 0){
                result.setCode(200);
                result.setMessage("注册成功");
                return result;
            }else{
                result.setCode(400);
                result.setMessage("用户名已存在");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
     * 发送验证码（邮箱验证）
     */

    @PostMapping("/sendEmail")
    public ResultObject sendEmail(@RequestParam("email") String email){
        try {
            String regex="[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.(com|cn|com\\.cn)";
            if (!email.matches(regex)){
                return new ResultObject(400,"邮箱格式有误",null);
            }
            //还要将验证存入redis中
            Integer i= userService.sendVerifyCode(email);
            if(i>0){
                return new ResultObject(200,"验证码发送成功",null);
            }else if(i==0){
                return new ResultObject(200,"验证码已发送",null);
            }else {
                return new ResultObject(400,"验证码发送失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



}
