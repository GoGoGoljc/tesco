package com.zpark.image.mapper;

import com.zpark.image.domain.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    Integer addImage(Image image);

    @Delete("delete from image where goods_id=#{goodsId}")
    Integer deleteByGoodsId(Integer goodsId);
}
