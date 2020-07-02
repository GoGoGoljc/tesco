package com.zkdj.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class pay {
    private Integer payId;
    private Integer payState;
    private Date payTime;
    private Double payMoney;
    private Integer orderId;
    private Integer userId;
    private Integer payType;
    private String goodsName;
}
