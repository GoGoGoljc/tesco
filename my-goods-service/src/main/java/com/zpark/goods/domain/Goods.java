package com.zpark.goods.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Goods implements Serializable {
    private Integer goodsId;
    private String goodsName;
    private Double goodsPrice;
    private Double goodsDiscount;
    private Integer goodsNum;
    private String goodsBrief;
    private  String goodsInfo;
    private Date goodsAddTime;
    private  Integer adminId;
    private  Integer ifNew;
    private Integer ifAdd;
    private  Integer secondSortId;
    private  Integer ifHot;
    private Integer sale;
    private String goodsImage;

}
