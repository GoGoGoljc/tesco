package com.zpark.stock.controller;

import com.zpark.stock.domain.Stock;
import com.zpark.stock.service.StockService;
import com.zpark.stock.util.CheckUtil;
import com.zpark.stock.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class UpdateStockController {

    @Autowired
    private StockService stockService;

    //根据商品id去修改商品的库存信息(库存信息)
    @PostMapping("/updateStock")
    public ResultObject updateStockByGoodsId(@RequestParam("goodsId") Integer goodsId,@RequestParam("goodsNum") Integer goodsNum){
        try {
            Boolean aBoolean = CheckUtil.checkIntNum(goodsId);
            if(!aBoolean){
                return  new ResultObject(400,"服务器繁忙,请重试",null);
            }

            Boolean aBoolean1 = CheckUtil.checkIntNum(goodsNum);
            if(!aBoolean1){
                return  new ResultObject(400,"服务器繁忙,请重试",null);
            }
            Stock stock = new Stock();
            stock.setGoodsId(goodsId);
            stock.setStockNum(goodsNum);
            Integer i=stockService.updateStockByGoodsId(stock);
            if(i>0){
                return new ResultObject(200,"库存信息更新成功",null);
            }
            return new ResultObject(400,"库存信息更新成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
