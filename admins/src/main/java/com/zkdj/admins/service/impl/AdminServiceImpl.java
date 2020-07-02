package com.zkdj.admins.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkdj.admins.domain.Admin;
import com.zkdj.admins.mapper.AdminMapper;
import com.zkdj.admins.service.AdminService;
import com.zkdj.admins.util.EmailUtil;
import com.zkdj.admins.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author SJXY
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Admin select(Admin admin) {
        return adminMapper.select(admin);
    }

    @Override
    public int addTokenToRedis(String token, Map<String, Object> data) {
        redisTemplate.opsForHash().putAll("adminToken:" + token, data);
        redisTemplate.expire("adminToken:" + token, 1, TimeUnit.DAYS);
        return 1;
    }

    @Override
    public void stockIntoRedis(Admin admin1) {
        redisTemplate.opsForList().leftPush("Loginadmin:"+admin1.getAdminId(),admin1);
    }

    @Autowired
    AdminMapper adminMapper;


    @Override
    public PageInfo<Admin> selectPages(Integer pageNum) {
        PageHelper.startPage(pageNum,8);
        List<Admin> adminsPages=this.adminMapper.selectPages();
        return new PageInfo<>(adminsPages);
    }

    @Override
    public int exit(Admin admin) {
        return adminMapper.exit(admin);
    }

    @Override
    public List<Admin> adminfindAll() {
       List<Admin> alist=adminMapper.adminfindAll();
        System.out.println(alist);
        return  alist;
    }


    @Override
    public Admin login(Admin admin) {
        return adminMapper.login(admin);
    }

    @Override
    public Integer State(Admin admin) {
        return adminMapper.State(admin);
    }

    @Override
    public int adminUpdate(Admin admin) {

       return   adminMapper.adminUpdate(admin);
    }

    @Override
    public Integer sendVerifyCode(String email) {
        //        先查是否存在已存在的验证码
        String existVerify = (String) redisTemplate.opsForValue().get(email);
//        判断验证码存不存在
        if (!StringUtils.isEmpty(existVerify)) {
            return 400 ;
        }
//        生成验证的位数
        String verify = VerifyUtil.generateVerify(6);
//        发送验证码
       EmailUtil.sendMessage("验证码","你的验证码是:"+verify,email,sender);
//       将验证码存入redis，并给他一个存活时间
        redisTemplate.opsForValue().set(email, verify, 60 * 1, TimeUnit.SECONDS);
        return 200;
    }



    @Override
    public List<Admin> selectByLike(Admin admin) {
        return adminMapper.selectByLike(admin);
    }

    @Override
    public int adminDelete(Integer adminId) {
         return adminMapper.adminDelete(adminId);
    }

    @Override
    public Admin adminfindById(Integer adminId) {
        return  adminMapper.adminfindById(adminId);
    }

    @Override
    public int insertAdmin(Admin admin) {
        return adminMapper.insertAdmin(admin);
    }
}
