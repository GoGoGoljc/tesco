package com.zkdj.userservice.service;

import com.github.pagehelper.PageInfo;
import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.domain.Verify;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户注册
     * @param users
     * @return
     */
    Integer userRegister(Users users);

    /**
     * 用户登录
     * @param users
     * @return
     */
    Users userLogin(Users users);

    /**
     * 更新用户信息
     * @param users
     * @return
     */
    int updateUserMessage(Users users);

    /**
     * 根据用户id查找用户信息
     * @param userId
     * @return
     */
    Users selectUserMessage(Integer userId);

    /**
     * 将登录用户信息存入redis中
     * @param userLogin
     */

    void stockIntoRedis(Users userLogin);

    /**
     * 发送验证码
     * @param email
     * @return
     */
    Integer sendVerifyCode(String email);

    /**
     * 从redis中获取验证码
     * @param userEmail
     * @return
     */
    String getCode(String userEmail);

    /**  //    验证用户名是否存在
     * 根据用户名查找用户
     * @param userName
     * @return
     */

    Users userNameExist(String userName);

    /**
     * 根据条件模糊查询用户
     * @param users
     * @return
     */

    List<Users> findUserByCondition(Users users);

    /**
     * 分页查询所有用户
     * @param pageNum
     * @return
     */
    PageInfo<Users> findUsersByPage(Integer pageNum);

    /**
     * 从redis缓存删除登录用户的信息
     * @param userId
     * @return
     */
    Integer deleteLoginUserFromRedis(Integer userId);

    /**
     *
     * @param token
     * @param user
     * @return
     */
   Integer  addTokenToRedis(String token, Map<String, Object> user);


    /**
     * 根据邮箱和用户id修改密码
     * @param verify
     * @return
     */
    Integer updatePasswordByEmail(Verify verify);

    /**
     * 校验验证码
     * @param verify
     * @return
     */
    Integer verifyCode(Verify verify);


    /**
     * 查询所有用户
     * @return
     */
    List<Users> findAll();

    Integer updatePwdByEmail(String email,String password);

    Integer deleteUserById(Integer userId);
}
