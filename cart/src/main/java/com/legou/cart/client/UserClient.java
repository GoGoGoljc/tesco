package com.legou.cart.client;


import com.legou.cart.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "user-service",fallback = UserClientFallback.class)
public interface UserClient {
    @PostMapping("/Login/selectUserMessage")
    Map selectUserMessage(@RequestParam("userId") Integer userId);
}
