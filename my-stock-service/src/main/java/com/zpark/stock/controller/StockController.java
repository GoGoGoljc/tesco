package com.zpark.stock.controller;

import com.ctc.wstx.util.StringUtil;
import com.zpark.stock.domain.Stock;
import com.zpark.stock.service.StockService;
import com.zpark.stock.util.CheckUtil;
import com.zpark.stock.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;


    //根据商品id查询库存信息
    @GetMapping("/findStockByGoodsId")
    public ResultObject findStockByGoodsId(@RequestParam("goods_id") Integer goodsId){
        try {
            Boolean aBoolean = CheckUtil.checkIntNum(goodsId);
            if(!aBoolean){
                return new ResultObject(400,"服务器繁忙，稍后再试",null);
            }
            Stock stock= stockService.findStockByGoodsId(goodsId);
            if(!StringUtils.isEmpty(stock)){
                return new ResultObject(200,"查询成功",stock);
            }else{
                return new ResultObject(200,"查询失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    //添加商品的时候  补上库存信息
    @PostMapping("/addStock")
    public ResultObject addStock(@RequestParam("stockNum") Integer stockNum,@RequestParam("goodsId") Integer goodsId,
                                 @RequestParam("stockPrice") double stockPrice){
        try {
            Boolean aBoolean = CheckUtil.checkIntNum(stockNum);
            if(!aBoolean){
                return new ResultObject(400,"服务器繁忙,请重试",null);
            }
            if(!StringUtils.isEmpty(stockNum) && !StringUtils.isEmpty(goodsId) && !StringUtils.isEmpty(stockPrice)){
                Stock stock = new Stock();
                //此处比较固定 不灵活
                stock.setHasStock(1);
                stock.setStockNum(stockNum);
                stock.setGoodsId(goodsId);
                stock.setStockPrice(stockPrice);

                //添加库存信息
                Integer i=stockService.addStock(stock);
                if(i>0){
                    return  new ResultObject(200,"添加库存信息成功",null);
                }
                return new ResultObject(400,"服务器繁忙,请稍候重试",null);

            }
            return new ResultObject(400,"服务器繁忙,请稍候重试",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
