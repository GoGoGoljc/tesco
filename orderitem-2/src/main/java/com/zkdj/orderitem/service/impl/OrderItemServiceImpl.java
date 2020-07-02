package com.zkdj.orderitem.service.impl;

import com.zkdj.orderitem.client.OrderClient;
import com.zkdj.orderitem.domain.Order;
import com.zkdj.orderitem.domain.OrderItem;
import com.zkdj.orderitem.mapper.OrderItemMapper;
import com.zkdj.orderitem.mapper.OrderMapper;
import com.zkdj.orderitem.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author SJXY
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Override
    public List<OrderItem> selectByLike(OrderItem orderItem) {
        return null;
    }

    @Autowired
    OrderItemMapper orderItemMapper;

    @Resource
    private OrderClient orderClient;

    @Resource
    private OrderMapper orderMapper;


    @Override
    public Integer deleteById(Integer orderItemId) {
        return orderItemMapper.deleteById(orderItemId);
    }

    @Override
    public Integer insert(OrderItem orderItem) {
        return orderItemMapper.insert(orderItem);
    }

    @Override
    public Integer update(OrderItem orderItem) {
        return orderItemMapper.update(orderItem);
    }

    @Override
    public OrderItem selectByState(OrderItem orderItem) {
        return orderItemMapper.selectByState(orderItem);
    }

    @Override
    public Integer insertOrderItem(OrderItem orderItem,Integer userId) {
        String substring = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        orderItem.setOrderNumber(substring);
        Integer i=orderItemMapper.insert(orderItem);
        if(i>0){
            Order order = new Order();
            order.setOrderCreateTime(new Date());
            order.setOrderState(0);
            order.setUserId(order.getUserId());
            order.setOrderUpdateTime(new Date());
            order.setUserId(userId);
            order.setOrderNumber(substring);
            order.setOrderMoney(orderItem.getTransactionNum()*orderItem.getTransactionPrice());
            Integer insert = orderMapper.insert(order);
            return insert;
        }
        return i;
    }

    @Override
    public OrderItem selectByNum(String orderNumber) {
        return orderItemMapper.selectByNum(orderNumber);
    }

    @Override
    public OrderItem selectById(Integer orderItemId) {
        return orderItemMapper.selectById(orderItemId);
    }

    @Override
    public List<OrderItem> selectByAll() {
        return orderItemMapper.selectByAll();
    }
}
