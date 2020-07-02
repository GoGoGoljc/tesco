package com.legou.cart.fallback;


import com.legou.cart.client.UserClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SJXY
 */
@Component
public class UserClientFallback implements UserClient {
    @Override
    public Map selectUserMessage(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "你的网络可能有问题哦！");
        return map;

    }
}
