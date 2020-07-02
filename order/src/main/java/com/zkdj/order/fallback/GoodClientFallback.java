package com.zkdj.order.fallback;

import com.zkdj.order.client.GoodClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author SJXY
 */
@Component
public class GoodClientFallback implements GoodClient {
    @Override
    public Map findById(Integer goodsId) {
        return null;
    }
}
