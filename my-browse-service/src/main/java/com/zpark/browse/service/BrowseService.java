package com.zpark.browse.service;

import com.zpark.browse.domian.Browse;

import java.util.List;

public interface BrowseService {
    Integer addBrowseInfo(Browse browse);

    List<Browse> findBrowsebyUserId(Integer userId);
}
