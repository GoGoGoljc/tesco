package com.zpark.browse.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zpark.browse.domian.Browse;
import org.springframework.util.StringUtils;

public class CheckBrowseUtil {

    public static Boolean checkBrowseAttr(Browse browse){
        Boolean flag=false;
        if(StringUtils.isEmpty(browse.getGoodsImage())){
           return flag;
        }

        if(StringUtils.isEmpty(browse.getGoodsName())){
            return flag;

        }
        if(StringUtils.isEmpty(browse.getGoodsId())){
            return flag;

        }
        if(StringUtils.isEmpty(browse.getUserId())){
            return flag;

        }
        flag=true;
        return flag;
    }

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

        if(num.intValue()<0){
            return flag;
        }
        flag=true;
        return flag;
    }


}
