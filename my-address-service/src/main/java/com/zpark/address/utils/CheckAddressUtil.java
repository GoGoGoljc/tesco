package com.zpark.address.utils;

import com.zpark.address.domain.Address;

import org.springframework.util.StringUtils;

public class CheckAddressUtil {

    public static Boolean CheckAttrIsEmpty(Address address) {
        //^1\d{10}$

        Boolean flag = false;
        if (!CheckAddressUtil.checkIntNum(address.getUserId())) {
            //进行了null ''
            flag = true;
            return flag;
        }

        if (StringUtils.isEmpty(address.getReceiverAddress())) {
            flag = true;
            return flag;
        }

        if (StringUtils.isEmpty(address.getReceiverName())) {
            flag = true;
            return flag;
        }

        return flag;
    }


    public static Boolean CheckPhone(Address address) {
        Boolean flag = false;
        String phoneReg = "^1[0-9]{10}$";
        if (!StringUtils.isEmpty(address.getReceiverPhone())) {
            if (address.getReceiverPhone().matches(phoneReg)) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }


    //比较饶 当初咋想的呀
    public static Boolean checkIntNum(Integer num) {
        Boolean flag = false;
        if (StringUtils.isEmpty(num + "")) {
            return flag;
        }

        if ("".equals(String.valueOf(num))) {
            return flag;
        }

        if ("null".equals(String.valueOf(num))) {
            return flag;
        }

        if (num.intValue() < 0) {
            return flag;
        }
        flag = true;
        return flag;
    }


    public static Boolean CheckSomeAttr(Address address) {
        if (StringUtils.isEmpty(address.getReceiverAddress()) && StringUtils.isEmpty(address.getReceiverName()) && StringUtils.isEmpty(address.getReceiverPhone())) {
            return false;
        }
        return true;
    }


}
