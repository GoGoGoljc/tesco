package com.zpark.collect.client;

import com.zpark.collect.fallback.GoodsClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "my-goods-service",fallback = GoodsClientFallback.class)
public interface GoodsClient {

    @GetMapping("/goods/findById")
    Map findById(@RequestParam("goods_id") Integer goodsId);

}
