package com.zpark.collect.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpark.collect.client.GoodsClient;
import com.zpark.collect.domain.Collect;
import com.zpark.collect.mapper.CollectMapper;
import com.zpark.collect.service.CollectService;
import com.zpark.collect.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private GoodsClient goodsClient;



    @Override
    public PageInfo<Collect> findAllByUserId(Integer userId, Integer pageNum) {
        try {
            PageHelper.startPage(pageNum, PageUtil.collectPageSize);
            List<Collect> collectList= collectMapper.findAllByUserId(userId);
            return new PageInfo<>(collectList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 添加商品收藏
     * @param collect
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public Integer addCollection(Collect collect) {
        try {
            //远程调用拿到商品的信息

            Map goodsResult = goodsClient.findById(collect.getCollectGoodsId());
            //拿到的是一个resultObject对象 里面封装了 根据id查询到商品信息
            //后面在设置熔断
            System.out.println("远程调用拿回来的数据"+goodsResult);
            Map goods = (Map) goodsResult.get("resultData");
            if(goods!=null){
                String goodsImage = (String) goods.get("goodsImage");
                String goodsName =(String) goods.get("goodsName");
                collect.setCollectTime(new Date());
                collect.setCollectGoodsImage(goodsImage);
                collect.setCollectGoodsName(goodsName);
                Integer i=collectMapper.insertSelective(collect);
                return i;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 取消收藏
     * @param collect
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public Integer cancelCollect(Collect collect) {
        try {
            Integer i=collectMapper.deleteByUserIdAndGoodsId(collect.getUserId(),collect.getCollectGoodsId());
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

    /**
     *根据用户id和商品id查看是否已经收藏了
     * @param userId
     * @param collectGoodsId
     * @return
     */

    @Override
    public Collect findCollectByUserIdAndGoodsId(Integer userId, Integer collectGoodsId) {
        try {
          Collect collect=collectMapper.findCollectByUserIdAndGoodsId(userId,collectGoodsId);
            System.err.println(collect);
            return collect;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }


    /**
     * 根据主键查询收藏的商品
     * @param collectId
     * @return
     */
    @Override
    public Collect findById(Integer collectId) {
        try {
            Collect collect = collectMapper.selectByPrimaryKey(collectId);
            return collect;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据主键删除收藏的商品
     * @param collectId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer deleteByPrimaryKey(Integer collectId) {
        try {
            int i = collectMapper.deleteByPrimaryKey(collectId);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Integer recordTotal(Integer userId) {
        try {
            Integer result=collectMapper.recordTotal(userId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
