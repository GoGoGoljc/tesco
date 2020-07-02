package com.zkdj.order.service;

import com.github.pagehelper.PageInfo;
import com.zkdj.order.domain.Order;

import java.util.List;

/**
 * @author SJXY
 */
public interface OrderService {
    List<Order> selectByAll();
    Order selectNum(String orderNumber);
    Integer selectById(Integer orderId);
    int insert(Order order);
    int update(Order order);
    int deleteByNum(String orderNumber);
    PageInfo<Order> selectPage(Integer pageNum);
    List<Order> selectByLike(Order order);


    List<Order> findOrderByUserId(Integer userId);
}
