package com.zkdj.userservice.mapper;

import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.domain.Verify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

//    用户注册
    Integer userRegister(Users users);

//    用户登录
    Users userLogin(Users users);

//    验证用户名是否存在
    Users userNameExist(String userName);

//    更新用户信息
    int updateUserMessage(Users users);

//    查询用户信息
    Users selectUserMessage(Integer userId);

    Integer insertSelective(Users users);

    List<Users> findUserByCondition(Users users);

    List<Users> findUsersByPage();

    int updatePasswordByEmail(Verify verify);

    List<Users> findAll();

    Integer updatePwdByEmail(String email,String password);

    Integer deleteUserById(Integer userId);
}
