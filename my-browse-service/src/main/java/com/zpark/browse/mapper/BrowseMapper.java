package com.zpark.browse.mapper;

import com.zpark.browse.domian.Browse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrowseMapper {

    Integer addBrowseInfo(Browse browse);

    List<Browse> findBrowsebyUserId(Integer userId);
}
