package com.zkdj.order.mapper;

import com.zkdj.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SJXY
 */
@Service
@Mapper
public interface OrderMapper {
    List<Order> seletByAll();
    Order selectByNum(String orderNumber);
    Integer selectById(Integer orderId);
    int insert(Order order);
    int update(Order order);
    int deleteByNum(String orderNumber);
    List<Order> selectPages();
    List<Order> selectByLike(Order order);

    List<Order> findOrderByUserId(Integer userId);
}
