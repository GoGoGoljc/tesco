package com.zpark.collect.fallback;

import com.zpark.collect.client.GoodsClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GoodsClientFallback implements GoodsClient {
    @Override
    public Map findById(Integer goodsId) {
        //这里得类型可以改得吗 还是map级别以下得都可以 我忘记了
        Map<String, Object> map = new HashMap<>();
        map.put("message","你的网络可能有问题哦！");
        return map;
    }
}
