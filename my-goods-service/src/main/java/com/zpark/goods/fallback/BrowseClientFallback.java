package com.zpark.goods.fallback;

import com.zpark.goods.client.BrowseClient;
import com.zpark.goods.domain.Goods;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class BrowseClientFallback implements BrowseClient {

    @Override
    public Map addBrowseInfo(String goodsName, String goodsImage, Integer goodsId, Integer userId) {
    Map map = new HashMap();
    map.put("browseFallbackInfo","远程调用添加浏览信息记录");
    return map;
    }
}
