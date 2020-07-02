package com.zkdj.orderitem.fallback;

import com.zkdj.orderitem.client.OrderClient;
import com.zkdj.orderitem.domain.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class OrderClientFallback implements OrderClient {
    @Override
    public Map insert(Order order) {
        Map<Object, Object> map = new HashMap<>();
        map.put("orderFallbakINfo","服务器异常");
        return map;
    }
}
