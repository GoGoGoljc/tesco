package com.zkdj.orderitem.client;

import com.zkdj.orderitem.domain.Order;
import com.zkdj.orderitem.fallback.OrderClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "order-service",fallback = OrderClientFallback.class,url = "http://172.18.84.32:9960/order-service")
public interface OrderClient {

    @PostMapping("/api/v1/order/insert")
     Map insert(Order order);
}
