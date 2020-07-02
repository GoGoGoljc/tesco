package com.zkdj.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkdj.order.client.GoodClient;
import com.zkdj.order.domain.Order;
import com.zkdj.order.mapper.OrderMapper;
import com.zkdj.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SJXY
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Resource
    GoodClient goodClient;
    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public int deleteByNum(String orderNumber) {
        return orderMapper.deleteByNum(orderNumber);
    }

    @Override
    public int update(Order order) {
        return orderMapper.update(order);
    }

    @Override
    public List<Order> selectByLike(Order order) {
        return orderMapper.selectByLike(order);
    }

    @Override
    public List<Order> findOrderByUserId(Integer userId) {
        return orderMapper.findOrderByUserId(userId);
    }

    @Override
    public PageInfo<Order> selectPage(Integer pageNum) {
        PageHelper.startPage(pageNum,8);
        List<Order> ordersPages=this.orderMapper.selectPages();
        return new PageInfo<>(ordersPages);
    }

    @Override
    public Order selectNum(String orderNumber) {
        return orderMapper.selectByNum(orderNumber);
    }

    @Override
    public Integer selectById(Integer orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override

    public List<Order> selectByAll() {


        return orderMapper.seletByAll();
    }
}
