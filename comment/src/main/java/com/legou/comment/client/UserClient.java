package com.legou.comment.client;

import com.legou.comment.fallback.GoodsClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "user-service", fallback = GoodsClientFallback.class)
public interface UserClient {
    @GetMapping("/user/findUserInfoById")
    Map findUserInfoById(@RequestParam("userId") Integer userId);
}
