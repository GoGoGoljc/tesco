package com.zpark.collect.service;

import com.github.pagehelper.PageInfo;
import com.zpark.collect.domain.Collect;

import java.util.List;

public interface CollectService {

    PageInfo<Collect> findAllByUserId(Integer userId, Integer pageNum);

    Integer addCollection(Collect collect);

    Integer cancelCollect(Collect collect);

    Collect findCollectByUserIdAndGoodsId(Integer userId, Integer collectGoodsId);

    Collect findById(Integer collectId);

    Integer deleteByPrimaryKey(Integer collectId);
}
