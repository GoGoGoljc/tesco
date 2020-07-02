package com.zkdj.orderitem.service.impl;

import com.zkdj.orderitem.domain.Order;
import com.zkdj.orderitem.mapper.OrderMapper;
import com.zkdj.orderitem.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl  implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }
}
