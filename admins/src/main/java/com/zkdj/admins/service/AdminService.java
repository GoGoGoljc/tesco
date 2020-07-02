package com.zkdj.admins.service;

import com.github.pagehelper.PageInfo;
import com.zkdj.admins.domain.Admin;

import java.util.List;
import java.util.Map;

/**
 * @author SJXY
 */
public interface AdminService {
    List<Admin> adminfindAll();
    PageInfo<Admin> selectPages(Integer pageNum);
    Admin adminfindById(Integer adminId);
    int insertAdmin(Admin admin);
   int adminDelete(Integer adminId);
    int adminUpdate(Admin admin);
    Admin login(Admin admin);
    int exit(Admin admin);
    Integer State(Admin admin);
    Admin select(Admin admin);

    List<Admin> selectByLike(Admin admin);

    int addTokenToRedis(String token, Map<String, Object> data);

    void stockIntoRedis(Admin admin1);
    Integer sendVerifyCode(String email);
}
