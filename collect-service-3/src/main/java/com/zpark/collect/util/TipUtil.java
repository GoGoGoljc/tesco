package com.zpark.collect.util;

public class TipUtil {

    public static ResultObject giveErrorTip(Boolean bBoolean){
        if(!bBoolean){
            System.out.println("sorry");
             ResultObject resultObject= new ResultObject(400,"抱歉，出故障了",null);
             return resultObject;
        }
        return null;
    }


}
