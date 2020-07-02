package com.zkdj.orderitem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author SJXY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer orderId;
    private String orderNumber;
    private Date orderCreateTime;
    private String orderRemark;
    private Integer userId;
    private Double orderMoney;
    private Date orderUpdateTime;
    private  Integer orderState;
}
