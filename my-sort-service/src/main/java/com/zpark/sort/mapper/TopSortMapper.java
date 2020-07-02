package com.zpark.sort.mapper;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.domain.TopSort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TopSortMapper {
    int deleteByPrimaryKey(Integer topSortId);

    int insert(TopSort record);

    int insertSelective(TopSort record);

    TopSort selectByPrimaryKey(Integer topSortId);

    int updateByPrimaryKeySelective(TopSort record);

    int updateByPrimaryKey(TopSort record);

    List<TopSort> findAll();

}