package com.zpark.image.service;

import com.zpark.image.domain.Image;

public interface ImageService {

    Integer addImage(Image image);

    Integer deleteByGoodsId(Integer goodsId);
}
