package com.zpark.address.controller;

import com.zpark.address.domain.Address;
import com.zpark.address.service.AddressService;
import com.zpark.address.utils.CheckAddressUtil;
import com.zpark.address.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressSelectController {

    @Autowired
    private AddressService addressService;
    /**
     * 根据用户的id查询所有的地址
     */

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/findAddressByUserId")
    public ResultObject findAddressByUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = (Integer)redisTemplate.opsForHash().get("userToken:"+token, "userId");
        try {
            if(!CheckAddressUtil.checkIntNum(userId)){
                return new ResultObject(400,"发生错误了",null);
            }
            List<Address> addressList=addressService.findAddressByUserId(userId);
            if(addressList!=null && addressList.size()>0){
                return new ResultObject(200,"查询成功",addressList);
            }
            return new ResultObject(400,"查询失败",addressList);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

    @PostMapping("/findAddressByUserId2")
    public ResultObject findAddressByUserId2(@RequestParam("userId") Integer userId){
        try {
            if(!CheckAddressUtil.checkIntNum(userId)){
                return new ResultObject(400,"发生错误了",null);
            }
            List<Address> addressList=addressService.findAddressByUserId(userId);
            if(addressList!=null && addressList.size()>0){
                return new ResultObject(200,"查询成功",addressList);
            }
            return new ResultObject(400,"查询失败",addressList);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }


    /**
     * 根据地址表的id去查地址信息
     */

    @GetMapping("/findAddressById")
    public ResultObject findAddressById(@RequestParam("address_id") Integer addressId){
        try {
            if(!CheckAddressUtil.checkIntNum(addressId)){
                return new ResultObject(400,"发生错误了",null);
            }
            Address address= addressService.findAddressById(addressId);
            if(address!=null){
                return new ResultObject(200,"查询成功",address);
            }
            return new ResultObject(400,"查询失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
