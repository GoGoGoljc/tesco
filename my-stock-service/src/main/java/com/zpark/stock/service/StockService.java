package com.zpark.stock.service;

import com.zpark.stock.domain.Stock;

public interface StockService {

    Stock findStockByGoodsId(Integer goodsId);

    Integer addStock(Stock stock);

    Integer updateStockByGoodsId(Stock stock);
}
