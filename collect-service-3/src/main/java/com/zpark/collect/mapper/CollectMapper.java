package com.zpark.collect.mapper;


import com.zpark.collect.domain.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper {

    int deleteByPrimaryKey(Integer collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<Collect> findAllByUserId(Integer userId);

    Integer deleteByUserIdAndGoodsId(Integer userId, Integer collectGoodsId);

    Collect findCollectByUserIdAndGoodsId(Integer userId, Integer collectGoodsId);

    Integer recordTotal(Integer userId);
}