package com.legou.comment.fallback;

import com.legou.comment.client.UserClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UsersClientFallback implements UserClient {

    @Override
    public Map findUserInfoById(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "你的网络可能有问题哦！");
        return map;
    }
}
