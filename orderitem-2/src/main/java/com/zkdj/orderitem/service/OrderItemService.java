package com.zkdj.orderitem.service;

import com.zkdj.orderitem.domain.Order;
import com.zkdj.orderitem.domain.OrderItem;

import java.util.List;

/**
 * @author SJXY
 */
public interface OrderItemService {
    List<OrderItem> selectByAll();
    OrderItem selectByNum(String orderNumber);
    OrderItem selectById (Integer orderItemId);
    Integer update(OrderItem orderItem);
    Integer insert(OrderItem orderItem);
    Integer deleteById(Integer orderItemId);
    List<OrderItem> selectByLike(OrderItem orderItem);
    OrderItem selectByState(OrderItem orderItem);

    Integer insertOrderItem(OrderItem orderItem,Integer userId);
}
