package com.zpark.stock.mapper;

import com.zpark.stock.domain.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StockMapper {
    int deleteByPrimaryKey(Integer stockId);

    int insert(Stock record);

    int insertSelective(Stock stock);

    Stock selectByPrimaryKey(Integer stockId);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    Stock findStockByGoodsId(Integer goodsId);


    Integer updateStockByGoodsId(Stock stock);
}