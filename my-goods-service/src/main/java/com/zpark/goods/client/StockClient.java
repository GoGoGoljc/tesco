package com.zpark.goods.client;

import com.zpark.goods.fallback.StockClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "my-stock-service",fallback = StockClientFallback.class)
public interface StockClient {

    @PostMapping("/stock/addStock")
    Map addStock(@RequestParam("stockNum") Integer stockNum, @RequestParam("goodsId") Integer goodsId,
                 @RequestParam("stockPrice") double stockPrice);

    @PostMapping("/stock/updateStock")
    String updateStockByGoodsId(@RequestParam("goodsId") Integer goodsId,@RequestParam("goodsNum") Integer goodsNum);

    @PostMapping("/stock/deleteStock")
    String deleteStockByGoodsId(@RequestParam("goodsId") Integer goodsId);
}
