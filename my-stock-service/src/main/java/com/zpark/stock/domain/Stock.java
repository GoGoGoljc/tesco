package com.zpark.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable {
    private Integer stockId;

    private Integer stockNum;

    private Integer hasStock;

    private Integer goodsId;

    private Double stockPrice;

}