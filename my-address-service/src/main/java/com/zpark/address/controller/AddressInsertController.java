package com.zpark.address.controller;

import com.zpark.address.domain.Address;

import com.zpark.address.service.AddressService;
import com.zpark.address.utils.CheckAddressUtil;
import com.zpark.address.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/address")
public class AddressInsertController {

    @Resource
    private AddressService addressService;

    /**
     * 用户表中有一个地址  那个是默认的地址
     * 添加收货地址
     */
    @PostMapping("/addAddress")
    public ResultObject addAddress(Address address){
        try {
            //因为是已经登录的用户 添加地址 前台可以把他的id给我
            //验证地址信息
            Boolean aBoolean = CheckAddressUtil.CheckAttrIsEmpty(address);
            if(aBoolean){
                return new ResultObject(400,"输入的信息不全",null);
            }

            if(!CheckAddressUtil.CheckPhone(address)){
                return new ResultObject(400,"电话格式不正确",null);
            }
            //添加地址
            Integer i=addressService.addAddress(address);
            if(i>0){
                return new ResultObject(200,"添加地址成功",null);
            }
            return new ResultObject(400,"添加地址失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }


}
