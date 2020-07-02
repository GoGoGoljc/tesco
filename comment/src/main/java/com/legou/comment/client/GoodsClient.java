package com.legou.comment.client;

import com.legou.comment.fallback.GoodsClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author SJXY
 */
@FeignClient(value = "my-goods-service", fallback = GoodsClientFallback.class,url = "http://172.18.84.32:9960/my-goods-service")
public interface GoodsClient {

    @GetMapping("/goods/findById")
    Map findById(@RequestParam("goods_id") Integer goodsId);

    @PostMapping("/goods/adminFindById")
    Map adminFindById(@RequestParam("goods_id") Integer goodsId, HttpServletRequest request);

}
