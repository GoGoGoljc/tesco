package com.zkdj.userservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkdj.userservice.domain.Users;
import com.zkdj.userservice.domain.Verify;
import com.zkdj.userservice.mapper.UserMapper;
import com.zkdj.userservice.service.UserService;
import com.zkdj.userservice.util.EmailUtil;
import com.zkdj.userservice.util.PageUtil;
import io.netty.util.internal.StringUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 用户注册
     * @param users
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer userRegister(Users users) {
        try {
//        验证用户名是否存在
            String userName = users.getUserName();
            if(StringUtils.isEmpty(userName)){
                return 0;
            }
            Integer i=0;
            users.setUserImage("image/user.jpg");
            users.setUserTime(new Date());
            Users users1 = userMapper.userNameExist(userName);
            if(users1==null){
                //用户名不存在，可以进行注册
              i=  userMapper.insertSelective(users);
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 用户登录
     * @param users
     * @return
     */
    @Override
    public Users userLogin(Users users) {
        return this.userMapper.userLogin(users);
    }

    /**
     *动态修改用户的信息 根据用户id
     * @param users
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public int updateUserMessage(Users users) {
        try {
            users.setUserTime(new Date());
            int i = userMapper.updateUserMessage(users);
            if(i>0){
                    //修改缓存  先删除  然后再存入
                    if(redisTemplate.hasKey("login-user:"+users.getUserId())){
                        redisTemplate.opsForList().rightPop("login-user:"+users.getUserId());
                }
                Users users1 = userMapper.selectUserMessage(users.getUserId());
                redisTemplate.opsForList().leftPush("login-user:"+users1.getUserId(),users1);

            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    @Override
    public Users selectUserMessage(Integer userId) {
        try {
            return userMapper.selectUserMessage(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 将登录用户信息存入redis中
     * @param userLogin
     */
    @Override
    //@Cacheable(cacheNames = "user:",key = "userLogin.userId")
    public void stockIntoRedis(Users userLogin) {
        redisTemplate.opsForList().leftPush("login-user:"+userLogin.getUserId(),userLogin);
    }


    /**
     * 发送邮件  把验证码存入redis中
     * @param email
     * @return
     */
    @Override
    public Integer sendVerifyCode(String email) {
        try {
            //先验证验证是否存在
            if(redisTemplate.hasKey(email)){
                //验证码已发送
                return 0;
            }
            String verifyCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
            //将验证码存入redis中
            System.out.println("verifyCode--------="+verifyCode);
            redisTemplate.opsForValue().set(email,verifyCode);
            redisTemplate.expire(email,120, TimeUnit.SECONDS);
            EmailUtil.sendMessage("验证码","你注册乐购商城的验证码："+verifyCode,email,javaMailSender);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据用户的邮箱key 从redis中获取用户的验证码
     * @param userEmail
     * @return
     */
    @Override
    public String getCode(String userEmail) {
        if(redisTemplate.hasKey(userEmail)){
            Object o = redisTemplate.opsForValue().get(userEmail);
          /*  if(StringUtils.isEmpty(o)){
                return null;
            }*/
            return (String) o;
        }
        return null;
    }
    /**
     * 判断用户名是否存在 根据用户名查询用户
     * @param userName
     * @return
     */

    @Override
    public Users userNameExist(String userName) {
        try {
            return  userMapper.userNameExist(userName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据条件模糊查询用户
     * @param users
     * @return
     */

    @Override
    public List<Users> findUserByCondition(Users users) {
        try {
            return userMapper.findUserByCondition(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 分页查找用户的信息
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Users> findUsersByPage(Integer pageNum) {
        try {
            PageHelper.startPage(pageNum,PageUtil.userPageSize);
            List<Users> usersList= userMapper.findUsersByPage();
            return new PageInfo<>(usersList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 从redis中删除登录用户的信息
     * @param userId
     * @return
     */
    @Override
    public Integer deleteLoginUserFromRedis(Integer userId) {
        if(redisTemplate.hasKey("login-user:"+userId)){
           redisTemplate.delete("login-user:"+userId);
           return 1;
        }
        return 0;
    }


    /**
     * 将用户的token信息存入redis
     * @param token
     * @param user
     */
    @Override
    public Integer addTokenToRedis(String token, Map<String, Object> user) {
        redisTemplate.opsForHash().putAll("userToken:" + token, user);
        redisTemplate.expire("userToken:" + token, 1, TimeUnit.HOURS);
        return 1;
    }

    @Override
    public Integer updatePasswordByEmail(Verify verify) {
        try {
            //修改密码
            int i=userMapper.updatePasswordByEmail(verify);
            //删除缓存  添加缓存
            if(i>0){
                Users users=userMapper.selectUserMessage(verify.userId);
                if(users!=null){
                    //删除缓存
                    if(redisTemplate.hasKey("login-user:"+verify.getUserId())){
                        redisTemplate.delete("login-user:"+verify.getUserId());
                        redisTemplate.opsForList().leftPush("login-user:"+verify.getUserId(),users);
                    }
                }
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 校验验证是否正确
     * @param verify
     * @return
     */
    @Override
    public Integer verifyCode(Verify verify) {
        try {
                String code = getCode(verify.userEmail);
            System.out.println("从redis中获取的数据验证码"+code);
                if(code==null){
                    //redis里面没有这个key
                    return 0;
                }
            System.out.println("-----------");
                //验证验证码是否正确
            System.out.println(verify.getCode()+"===="+code);
                if(verify.getCode().equals(code)){
                    //验证码正确
                    return 1;
                }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询所有用户
     * @return
     */

    @Override
    public List<Users> findAll() {
        try {
            return  userMapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public Integer updatePwdByEmail(String email,String password) {
        try {
            return userMapper.updatePwdByEmail(email,password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据用户id删除用户
     * @param userId
     * @return
     */
    @Override
    public Integer deleteUserById(Integer userId) {
        try {
            return userMapper.deleteUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


}
