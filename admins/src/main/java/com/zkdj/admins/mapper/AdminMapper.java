package com.zkdj.admins.mapper;

import com.zkdj.admins.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;

/**
 * @author SJXY
 */
@Service
@Mapper
public interface AdminMapper {
    List<Admin> adminfindAll();
    Admin adminfindById(Integer adminId);
    int insertAdmin(Admin admin);
    int adminDelete(Integer adminId);
    int adminUpdate(Admin admin);
    Admin login(Admin admin);
  int exit(Admin admin);
    List<Admin> selectPages();
    List<Admin> selectByLike(Admin admin);
    Integer State(Admin admin);
    Admin select(Admin admin);

}
