package com.zkdj.orderitem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SJXY
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    private Integer orderItemId;
    private String  orderNumber;
    private  Integer goodsId;
    private  String goodsName;
    private  Double transactionPrice;
    private  Integer transactionNum;
    private  Double orderItemSubtotal;
    private String  goodsImage;
    private  Integer orderState;

}
