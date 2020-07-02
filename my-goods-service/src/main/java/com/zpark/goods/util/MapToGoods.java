package com.zpark.goods.util;

import com.zpark.goods.domain.Goods;
import org.omg.CORBA.INTERNAL;
import org.springframework.data.relational.core.sql.In;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

public class MapToGoods {

    public  static Goods mapToGoods(Map<Object,Object> map){
        //空检验
        if(map==null){
            return null;
        }
        Goods goods = new Goods();
        if(!StringUtils.isEmpty(map.get("adminId").toString())){
            goods.setAdminId(Integer.parseInt(map.get("adminId").toString()));
        }

        if(!StringUtils.isEmpty(map.get("addTime").toString())){
            goods.setGoodsAddTime(new Date(map.get("addTime").toString()));
        }

        if(!StringUtils.isEmpty(map.get("goodsBrief").toString())){
            goods.setGoodsBrief(map.get("goodsBrief").toString());
        }

        if(!StringUtils.isEmpty(map.get("goodsDiscount").toString())){
            goods.setGoodsDiscount(Double.parseDouble(map.get("goodsDiscount").toString()));
        }

        if(!StringUtils.isEmpty(map.get("goodsId").toString())){
            goods.setGoodsId(Integer.parseInt(map.get("goodsId").toString()));

        }

        if(!StringUtils.isEmpty(map.get("goodsImage").toString())){
            goods.setGoodsImage(map.get("goodsImage").toString());
        }

        if(!StringUtils.isEmpty(map.get("goodsInfo").toString())){
            goods.setGoodsInfo(map.get("goodsInfo").toString());
        }


        if(!StringUtils.isEmpty(map.get("goodsName").toString())){
            goods.setGoodsName(map.get("goodsName").toString());
        }

        if(!StringUtils.isEmpty(map.get("goodsPrice").toString())){
            goods.setGoodsPrice(Double.parseDouble(map.get("goodsPrice").toString()));
        }

        if(!StringUtils.isEmpty(map.get("ifAdd").toString())){
            goods.setIfAdd(Integer.parseInt(map.get("ifAdd").toString()));
        }

        if(!StringUtils.isEmpty(map.get("ifHot").toString())){
            goods.setIfHot(Integer.parseInt(map.get("ifHot").toString()));
        }

        if(!StringUtils.isEmpty(map.get("goodsNum").toString())){
            goods.setGoodsNum(Integer.parseInt(map.get("goodsNum").toString()));
        }

        if(!StringUtils.isEmpty(map.get("ifNew").toString())){
            goods.setIfNew(Integer.parseInt(map.get("ifNew").toString()));
        }

        if(!StringUtils.isEmpty(map.get("sale").toString())){
            goods.setSale(Integer.parseInt(map.get("sale").toString()));
        }
        if(!StringUtils.isEmpty(map.get("secondSortId").toString())){
            goods.setSecondSortId(Integer.parseInt(map.get("secondSortId").toString()));
        }
        return goods;
       /*
        goods.setAdminId(Integer.parseInt(map.get("adminId").toString()));
        goods.setGoodsAddTime(new Date(map.get("addTime").toString()));
        goods.setGoodsBrief(map.get("goodsBrief").toString());
        goods.setGoodsDiscount(Double.parseDouble(map.get("goodsDiscount").toString()));
        goods.setGoodsId(Integer.parseInt(map.get("goodsId").toString()));
        goods.setGoodsImage(map.get("goodsImage").toString());
        goods.setGoodsInfo(map.get("goodsInfo").toString());
        goods.setGoodsName(map.get("goodsName").toString());
        goods.setGoodsPrice(Double.parseDouble(map.get("goodsPrice").toString()));
        goods.setIfAdd(Integer.parseInt(map.get("ifAdd").toString()));
        goods.setIfHot(Integer.parseInt(map.get("ifHot").toString()));
        goods.setGoodsNum(Integer.parseInt(map.get("goodsNum").toString()));
        goods.setIfNew(Integer.parseInt(map.get("ifNew").toString()));
        goods.setSale(Integer.parseInt(map.get("sale").toString()));
        goods.setSecondSortId(Integer.parseInt(map.get("secondSortId").toString()));*/
    }
}
