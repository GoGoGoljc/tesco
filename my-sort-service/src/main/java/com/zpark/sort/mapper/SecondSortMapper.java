package com.zpark.sort.mapper;

import com.zpark.sort.domain.SecondSort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecondSortMapper {
    int deleteByPrimaryKey(Integer secondSortId);

    int insert(SecondSort record);

    int insertSelective(SecondSort record);

    SecondSort selectByPrimaryKey(Integer secondSortId);

    int updateByPrimaryKeySelective(SecondSort record);

    int updateByPrimaryKey(SecondSort record);

    List<SecondSort> findSecondSortByTopSortId(Integer topSortId);

    List<SecondSort> findAllSecondSort();
}