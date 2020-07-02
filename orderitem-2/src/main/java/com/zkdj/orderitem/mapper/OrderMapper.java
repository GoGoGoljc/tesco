package com.zkdj.orderitem.mapper;

import com.zkdj.orderitem.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int insert(Order order);
}
