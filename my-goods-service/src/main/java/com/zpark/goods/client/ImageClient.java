package com.zpark.goods.client;

import com.zpark.goods.fallback.ImageClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "my-image-service",fallback = ImageClientFallback.class,url = "http://172.18.84.32:9960/my-image-service")
public interface ImageClient {
    @PostMapping("/image/deleteByGoodsId")
    Map deleteByGoodsId(@RequestParam("goods_id") Integer goodsId);

}
