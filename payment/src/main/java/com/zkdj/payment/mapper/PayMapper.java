package com.zkdj.payment.mapper;

import com.alipay.api.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PayMapper {
    OrderItem selectByOrderItemId(Integer orderItemId);
}
