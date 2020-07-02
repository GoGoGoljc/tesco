package com.zpark.goods.controller;

import com.zpark.goods.domain.Goods;
import com.zpark.goods.service.GoodsService;
import com.zpark.goods.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/goods")
public class RankController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品热销榜
     */

    @GetMapping("/findTopGoods")
    public ResultObject findTopGoods(){
        try {
            //先根据销量排序查出前几名的商品（还是查所有呢）
         //   Set<Goods> topCoodsList=  goodsService.findTopGoods();
            List<Object> topCoodsList=  goodsService.findTopGoods();

            for (Object goods: topCoodsList) {
                System.out.println("---------------"+goods);
            }
            if(topCoodsList!=null && topCoodsList.size()>0){
                return new ResultObject(200,"查询成功",topCoodsList);
            }
            return new ResultObject(400,"查询失败",null);
            //根据销量将商品存入redis中

            //还要设置生存时间
            //查询redis  的前几名商品
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据销量查询前几名的商品
     */

 /*   @GetMapping("/findTopGoodsBySale")
    public  List<Goods> findTopGoodsBySale(){
        try {
            List<Goods> topGoodsList=goodsService.findTopGoodsBySale();
            return topGoodsList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }*/
}
