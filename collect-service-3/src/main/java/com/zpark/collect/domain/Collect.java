package com.zpark.collect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Collect<T> {
    private Integer collectId;

    private Integer collectGoodsId;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectTime;

    private Integer userId;

    private String collectGoodsImage;

    private String collectGoodsName;


    //private List<T> goodsList;

}