package com.zpark.address.controller;

import com.zpark.address.domain.Address;
import com.zpark.address.service.AddressService;
import com.zpark.address.utils.CheckAddressUtil;
import com.zpark.address.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressUpdateController {

    @Autowired
    private AddressService addressService;

    /**
     * 动态修改地址信息（根据用户id）
     */

    @PostMapping("/updateAddressById")
    public ResultObject updateAddressById(Address address){
        try {
            System.out.println("请求修改地址");
            if(!CheckAddressUtil.checkIntNum(address.getAddressId())){
                return new ResultObject(400,"发生错误了",null);
            }
            //校验其他信息是否为空  万一除了id以外 全部为空了呢  就报错了
            if(!CheckAddressUtil.CheckSomeAttr(address)){
                return new ResultObject(400,"输入的信息不能全为空",null);
            }

            //检验电话
            if(!StringUtils.isEmpty(address.getReceiverPhone())){
                if(!CheckAddressUtil.CheckPhone(address)){
                    return new ResultObject(400,"电话格式不正确",null);
                }
            }

            Integer i=addressService.updateAddressById(address);
            if(i>0){
                return new ResultObject(200,"修改地址成功",null);
            }
            return new ResultObject(400,"修改地址失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
