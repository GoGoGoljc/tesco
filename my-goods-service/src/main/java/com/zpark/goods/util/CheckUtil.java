package com.zpark.goods.util;

import com.zpark.goods.domain.Goods;
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


    public static Boolean checkGoodsAttribute(Goods goods){

        Boolean flag=false;

        if(StringUtils.isEmpty(goods)){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getGoodsImage())){
            return flag;
        }

        if (StringUtils.isEmpty(goods.getGoodsDiscount())){
            return flag;
        }

        if(StringUtils.isEmpty(goods.getGoodsName())){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getGoodsBrief())){
            return flag;
        }
       /* if(StringUtils.isEmpty(goods.getGoodsInfo())){
            return flag;
        }*/
        if(StringUtils.isEmpty(goods.getGoodsPrice())){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getSecondSortId())){
            return flag;
        }

        if(StringUtils.isEmpty(goods.getGoodsNum())){
            return flag;
        }

        flag=true;
        return flag;
    }


    public static Boolean checkGoodsAttribute2(Goods goods){

        Boolean flag=false;

        if(StringUtils.isEmpty(goods)){
            return flag;
        }


        if (StringUtils.isEmpty(goods.getGoodsDiscount())){
            return flag;
        }

        if(StringUtils.isEmpty(goods.getGoodsName())){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getGoodsBrief())){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getGoodsInfo())){
            return flag;
        }
        if(StringUtils.isEmpty(goods.getGoodsPrice())){
            return flag;
        }

        if(StringUtils.isEmpty(goods.getGoodsNum())){
            return flag;
        }

        flag=true;
        return flag;
    }

}
