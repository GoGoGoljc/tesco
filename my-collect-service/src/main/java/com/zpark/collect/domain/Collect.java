package com.zpark.collect.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Collect<T> {
    private Integer collectId;

    private Integer collectGoodsId;

    private Date collectTime;

    private Integer userId;

    private String collectGoodsImage;

    private String collectGoodsName;



    //private List<T> goodsList;

}