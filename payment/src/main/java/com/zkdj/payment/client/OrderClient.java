package com.zkdj.payment.client;

import com.zkdj.payment.fallback.OrderClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
//远程调用的方法
@FeignClient(value = "orderitem-service",fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/v1/orderitem/selectById")
    Map selectById(@RequestParam  Integer orderItemId);
}
