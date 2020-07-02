package com.zpark.stock.service.impl;

import com.zpark.stock.domain.Stock;
import com.zpark.stock.mapper.StockMapper;
import com.zpark.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private  StockMapper stockMapper;

    @Override
    public Stock findStockByGoodsId(Integer goodsId) {
        try {
            Stock stock= stockMapper.findStockByGoodsId(goodsId);
            return stock;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer addStock(Stock stock) {
        try {
            System.out.println("执行了addStock方法-------------------------");
            //int m=10/0;
            Integer i=stockMapper.insert(stock);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 更新库存信息
     * @param stock
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer updateStockByGoodsId(Stock stock) {
        try {
           // int age=10/0;
            return stockMapper.updateStockByGoodsId(stock);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
