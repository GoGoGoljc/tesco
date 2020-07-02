package com.zpark.goods.util;

import java.util.List;

public class CommonUtil {

    public  static ResultObject listResultTip(List list){
        ResultObject result = new ResultObject();
        if(list!=null && list.size()>0){
            result.setCode(200);
            result.setMessage("查询成功");
            result.setResultData(list);
            return result;
        }
        result.setCode(400);
        result.setMessage("没有查询结果");
        return  result;
    }
}
