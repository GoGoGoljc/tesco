package com.zpark.collect.util;

import org.springframework.util.StringUtils;

public class CheckUtil {

    public static  Boolean checkIntNum(Integer num){
        Boolean flag=false;
        if(StringUtils.isEmpty(num+"")){
            return flag;
        }

       if("".equals(String.valueOf(num))){
           return flag;
       }

       if("null".equals(String.valueOf(num))){
            return flag;
        }

        if("".equals(String.valueOf(num))){
            return flag;
        }
        if(num.intValue()<0){
            return flag;
        }
        flag=true;
        return flag;
    }
}
