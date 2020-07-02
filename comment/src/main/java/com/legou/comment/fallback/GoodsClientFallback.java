package com.legou.comment.fallback;

import com.legou.comment.client.GoodsClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class GoodsClientFallback implements GoodsClient {
    @Override
    public Map findById(Integer goodsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "你的网络可能有问题哦！");
        return map;
    }

    @Override
    public Map adminFindById(Integer goodsId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "你的网络可能有问题哦！");
        return map;
    }
}
