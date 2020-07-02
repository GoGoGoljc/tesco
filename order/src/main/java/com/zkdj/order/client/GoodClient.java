package com.zkdj.order.client;

import com.zkdj.order.fallback.GoodClientFallback;
import com.zkdj.order.util.ResultObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author SJXY
 */
@FeignClient(value = "my-goods-service",fallback = GoodClientFallback.class)
public interface GoodClient {
  @GetMapping("/goods/findById")
     Map findById(@RequestParam("goods_id") Integer goodsId);
}
