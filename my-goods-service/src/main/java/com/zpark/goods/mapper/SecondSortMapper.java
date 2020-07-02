package com.zpark.goods.mapper;

import com.zpark.goods.domain.SecondSort;

public interface SecondSortMapper {
    int deleteByPrimaryKey(Integer secondSortId);

    int insert(SecondSort record);

    int insertSelective(SecondSort record);

    SecondSort selectByPrimaryKey(Integer secondSortId);

    int updateByPrimaryKeySelective(SecondSort record);

    int updateByPrimaryKey(SecondSort record);
}