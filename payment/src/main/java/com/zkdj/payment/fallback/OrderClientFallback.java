package com.zkdj.payment.fallback;

import com.zkdj.payment.client.OrderClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderClientFallback implements OrderClient {
//远程调用出现错误时的降级处理
    @Override
    public Map selectById(Integer orderId) {
        Map map = new HashMap();
        map.put("info","对方服务器繁忙，请稍候再试");
        return map;
    }


}
