package com.zpark.goods.service;

import com.github.pagehelper.PageInfo;
import com.zpark.goods.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;


public interface GoodsService {

   PageInfo<Goods> findAllGoods(Integer pageNum);
//List<Goods> findAllGoods(Integer pageNum);

    List<Goods> findNewGoods();

    List<Goods> findHotGoods();

    Goods findById(Integer goodsId);

    Integer addGoods(Goods goods);

    Integer findRecordCount();

    Integer deleteById(Integer goodsId);

    Goods updateById(Goods goods);

    Goods findByIdNoCache(Integer goodsId);

    //查找指定页的数据
    List<Goods> findBySpecifiedPage(Integer pageNum);

    PageInfo<Goods> searchGoods(Goods goods,Integer pageNum);

    PageInfo<Goods> findGoodsBySecondSortId(Integer secondSortId,Integer pageNum);

    List<Goods> findTopGoodsBySale();

    //Set<Goods> findTopGoods();
    List<Object> findTopGoods();

    List<Goods> findBargain();

    Integer addGoodsBrowseInfo(Goods goods, Integer userId);

 PageInfo<Goods> findUsersByPage(Integer pageNum);

 List<Goods> searchGoods2(Goods goods);

 List<Goods> selectTopGoods();

 List<Goods> findGoodsBySecondSortIdNoPage(Integer secondSortId, Integer pageNum);

 List<Goods> selectTopGoods2();
}
