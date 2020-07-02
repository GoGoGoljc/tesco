package com.zpark.goods.mapper;

import com.github.pagehelper.PageInfo;
import com.zpark.goods.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface GoodsMapper {

    List<Goods> findAllGoods();

    List<Goods> findNewGoods();

    List<Goods> findHotGoods();

    Goods selectByPrimaryKey(Integer goodsId);

    Integer insert(Goods goods);

    Integer insertSelective(Goods goods);

    Integer findRecordCount();

    Integer deleteById(Integer goodsId);

    Integer updateByPrimaryKeySelective(Goods goods);

    List<Goods> findBySpecifiedPage(Integer pageNum);

    List<Goods> searchGoods(Goods goods);

    List<Goods> findGoodsBySecondSortId(Integer secondSortId);

    Integer findMaxGoodsId();

    List<Goods> findTopGoodsBySale();

    List<Goods> findBargain();

    List<Goods> findUsersByPage();

    List<Goods> selectTopGoods();

    List<Goods> selectTopGoods2();
}
