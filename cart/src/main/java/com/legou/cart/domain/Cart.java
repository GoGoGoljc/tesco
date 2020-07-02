package com.legou.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SJXY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Integer userId;
    private Integer goodId;
    private Integer goodNum;
    private String goodName;
    private Double goodPrice;
    private Double goodDiscount;
    private String goodImage;
    private Double goodTotal;

    public void getGoodImage(String goodImage) {
    }
}
