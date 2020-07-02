package com.zkdj.orderitem.mapper;

import com.zkdj.orderitem.domain.Order;
import com.zkdj.orderitem.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SJXY
 */
@Mapper
@Service
public interface OrderItemMapper {
    List<OrderItem> selectByAll();
    OrderItem selectByNum(String orderNumber);
    OrderItem selectById(Integer orderItemId);
    Integer update(OrderItem orderItem);
    Integer insert (OrderItem orderItem);
    Integer deleteById(Integer orderItemId);
    OrderItem selectByState(OrderItem orderItem);
}
