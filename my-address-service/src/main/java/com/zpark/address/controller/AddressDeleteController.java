package com.zpark.address.controller;

import com.zpark.address.service.AddressService;
import com.zpark.address.utils.CheckAddressUtil;
import com.zpark.address.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressDeleteController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/deleteAddressById")
    public ResultObject deleteAddressById(@RequestParam("address_id") Integer addressId){
        try {
            if(!CheckAddressUtil.checkIntNum(addressId)){
                return new ResultObject(400,"发生错误了",null);
            }
            Integer i= addressService.deleteAddressById(addressId);
            if(i>0){
                return new ResultObject(200,"删除地址成功",null);
            }
            return new ResultObject(400,"删除地址失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
