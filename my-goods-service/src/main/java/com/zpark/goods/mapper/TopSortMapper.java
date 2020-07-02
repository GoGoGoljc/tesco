package com.zpark.goods.mapper;

import com.zpark.goods.domain.TopSort;

public interface TopSortMapper {
    int deleteByPrimaryKey(Integer topSortId);

    int insert(TopSort record);

    int insertSelective(TopSort record);

    TopSort selectByPrimaryKey(Integer topSortId);

    int updateByPrimaryKeySelective(TopSort record);

    int updateByPrimaryKey(TopSort record);
}