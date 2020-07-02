package com.zkdj.payment.service.impl;

import com.zkdj.payment.client.OrderClient;
import com.zkdj.payment.domain.pay;

import com.zkdj.payment.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

@Service

public class AlipayServiceImpl implements AlipayService {

    @Resource
    private OrderClient orderClient;



    @Override
    public pay getOrderItem(Integer orderItemId) {
        Map order = orderClient.selectById(orderItemId);
        System.out.println(order+"------------");
        Map orderItem = (Map) order.get("resultData");
        System.out.println(orderItem);
        if (!StringUtils.isEmpty(orderItem)){
            Double subtotal = (Double) orderItem.get("orderItemSubtotal");
            String goodsName = (String) orderItem.get("goodsName");
            pay pay = new pay();
            pay.setPayMoney(subtotal);
            pay.setGoodsName(goodsName);
            System.out.println(pay);
            return  pay;
        }
       return null;
    }


}
