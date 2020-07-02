package com.zpark.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpark.goods.client.BrowseClient;
import com.zpark.goods.client.ImageClient;
import com.zpark.goods.client.StockClient;
import com.zpark.goods.domain.Goods;
import com.zpark.goods.mapper.GoodsMapper;
import com.zpark.goods.service.GoodsService;
import com.zpark.goods.util.PageUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StockClient stockClient;

    @Resource
    private BrowseClient browseClient;

    @Resource
    private ImageClient imageClient;


    /**
     * 分页查询所有商品
     *
     * @param pageNum
     * @return
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageInfo<Goods> findAllGoods(Integer pageNum) {
        try {
            if (redisTemplate.hasKey("pageGoods" + pageNum)) {
                List<List> allGoods = redisTemplate.opsForList().range("pageGoods" + pageNum, 0, -1);
                System.out.println("从缓存中取数据" + allGoods);
                return new PageInfo<>(allGoods.get(0));
            }
            PageHelper.startPage(pageNum, PageUtil.PageSize);
            List<Goods> goodsList = goodsMapper.findAllGoods();
            redisTemplate.opsForList().leftPush("pageGoods" + pageNum, goodsList);
            redisTemplate.expire("pageGoods" + pageNum, 5, TimeUnit.MINUTES);
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            System.out.println("从数据库中取数据");
            return pageInfo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


//    @Override
//    @Transactional(propagation=Propagation.REQUIRED,readOnly = true)
//    public List<Goods> findAllGoods(Integer pageNum) {
//        try {
//            if(redisTemplate.hasKey("pageGoods"+pageNum)){
//                List<Goods> allGoods = redisTemplate.opsForList().range("pageGoods"+pageNum, 0, -1);
//                System.out.println("从缓存中取数据"+allGoods);
//                return  allGoods;
//            }
//            List<Goods>  goodsList=goodsMapper.findAllGoods();
//            redisTemplate.opsForList().leftPush("pageGoods"+pageNum,goodsList);
//            redisTemplate.expire("pageGoods"+pageNum, 5, TimeUnit.MINUTES);
//            System.out.println("从数据库中取数据");
//            return goodsList ;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }

    /**
     * 查询最新商品
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Goods> findNewGoods() {
        try {
            List<Goods> goodsList = goodsMapper.findNewGoods();
            return goodsList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 查询最热商品
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Goods> findHotGoods() {
        try {
            List<Goods> goodsList = goodsMapper.findHotGoods();
            return goodsList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 根据id查询商品信息
     * @param goodsId
     * @return
     */
  /*  @Override
    public Goods findById(Integer goodsId) {

        try {
            if(redisTemplate.hasKey("goodsId"+goodsId)){
                System.out.println("从redis缓存中取数据");
                //为啥报错呢
//                Goods goods= (Goods) redisTemplate.opsForList().range("goodsId"+goodsId,0,-1);
                Goods goods= (Goods) redisTemplate.opsForValue().get("goodsId"+goodsId);
                return goods;
            }
            System.out.println("从数据库中取数据");
            Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
          //  redisTemplate.opsForList().leftPush("goodsId"+goodsId,goods);
          //  redisTemplate.expire("goodsId"+goodsId, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set("goodsId"+goodsId,goods,10,TimeUnit.SECONDS);
            return goods;
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }*/

    /**
     * 根据商品idc查询商品信息
     *
     * @param goodsId
     * @return
     */

    @Override
    @Cacheable(cacheNames = "goods_id", key = "#goodsId")
    public Goods findById(Integer goodsId) {
        //Thread.sleep(10000);
//      if(redisTemplate.hasKey("goods_id"+goodsId)){
//        return MapToGoods.mapToGoods((Map<Object, Object>) redisTemplate.opsForValue().get("goods_id" + goodsId));
//      }
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        //   redisTemplate.opsForValue().set("goods_id"+goodsId,goods);
        return goods;
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer addGoods(Goods goods) {
        //刚添加的商品要加入缓存中码
        //更新分页的缓存
        try {
            //添加商品的时候 把 库存信息给添了 不然啥时候添呢
            Integer findGoodsId = goodsMapper.findMaxGoodsId();
            findGoodsId++;
            Map s = stockClient.addStock(goods.getGoodsNum(), findGoodsId, goods.getGoodsPrice());
            //如何判断调用的结果已经成功呢？
            if (!StringUtils.isEmpty(s.get("fallbackInfo"))) {
                System.out.println("fallbackInfo" + s.get("fallbackInfo"));
                //已经熔断 或者服务降级了
                //事务呢  好像那边已经帮我弄了事务回滚  只要把insertSelective放在后面就可以了 出错也会回滚
                return 0;
            }
            Integer result = goodsMapper.insertSelective(goods);
            System.out.println("返回来的result=" + findGoodsId + "-----" + s);
            if (result > 0 && findGoodsId > 0) {
                Integer recordCount = goodsMapper.findRecordCount();
                int pageNum = recordCount / PageUtil.PageSize;

                if (recordCount % PageUtil.PageSize != 0) {
                    pageNum++;
                }
                System.out.println("第几页" + pageNum);
                // List<Goods> goodsList=goodsMapper.findBySpecifiedPage((pageNum-1)*PageUtil.PageSize);
                //调用查找分页查找全部的方法    要不把那一页缓存给删了  好像不ok
                // updateAllGoodsByPage(pageNum);
                //为啥你还要把那个缓存给查出来呢，直接让他没有不好吗，真是的
                //我管你差几条，我只要那个新插入的那条在哪页
                // redisTemplate.opsForList().leftPush("pageGoods"+pageNum,goodsList);
                // redisTemplate.expire("pageGoods"+pageNum, 5, TimeUnit.MINUTES);
                //删除那个分页的缓存
                if(redisTemplate.hasKey("pageGoods"+pageNum)){
                    System.out.println("删除分页缓存");
                    redisTemplate.opsForList().rightPop("pageGoods"+pageNum);
                }


            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


 /*   @Override
    public PageInfo<Goods> updateAllGoodsByPage(Integer pageNum) {
        try {

            PageHelper.startPage(pageNum, PageUtil.PageSize);
            List<Goods>  goodsList=goodsMapper.findAllGoods();
            redisTemplate.opsForList().leftPush("pageGoods"+pageNum,goodsList);
            redisTemplate.expire("pageGoods"+pageNum, 5, TimeUnit.MINUTES);
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            System.out.println("从数据库中取数据");
            return pageInfo ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
*/

    /**
     * 根据商品id删除商品
     *
     * @param goodsId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @CacheEvict(cacheNames = "goods_id", key = "#goodsId")
    public Integer deleteById(Integer goodsId) {
        try {
            //远程调用删除商品图片
            Map map = imageClient.deleteByGoodsId(goodsId);
            System.err.println(map);
            if (map.get("imageFallbackInfo") != null) {
                //不为空，进行服务降级
                System.out.println(map.get("imageFallbackInfo")+"------------------------------");
                return 0;
            }
            String s = stockClient.deleteStockByGoodsId(goodsId);
            if (StringUtils.isEmpty(s)) {
                System.out.println("库存有问题");
                //进行了服务降级
                return 0;
            }
            System.out.println("执行service层的deleteById方法");
            Integer i = goodsMapper.deleteById(goodsId);
            //是删除数据库再删缓存码
            //beforeInvocation 是 @CacheEvict 中特有的一个属性，意为是否在执行对应方法之前删除缓存，默认 false（即执行方法之后再删除缓存）

            //后面加的 因为分页查询中缓存没更新  修改后还要将数据查询出来，不清除的话还是分页中缓存的数据
            Set keys = redisTemplate.keys("pageGoods*");
            for (Object key : keys) {
                if (key.toString().startsWith("pageGoods")) {
                    System.out.println("删除缓存");
                    redisTemplate.opsForList().rightPop(key);
                }
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    @CachePut(cacheNames = "goods_id", key = "#goods.goodsId")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Goods updateById(Goods goods) {
        try {
            //如果更新的是库存信息的话 我还要远程修改库存信息
            if (!StringUtils.isEmpty(goods.getGoodsNum())) {
                //修改库存 根据商品id   并把商品的数量传过去
                //库存的价格就不要修改了
                String s = stockClient.updateStockByGoodsId(goods.getGoodsId(), goods.getGoodsNum());
                //判断远程请求的结果是否成功
                if (StringUtils.isEmpty(s)) {
                    //发生熔断或者服务降级啥的
                    return null;
                }
            }
            Integer result = goodsMapper.updateByPrimaryKeySelective(goods);
            //后面加的 因为分页查询中缓存没更新  修改后还要将数据查询出来，不清除的话还是分页中缓存的数据

            Set keys = redisTemplate.keys("pageGoods*");
            for (Object key : keys) {
                if (key.toString().startsWith("pageGoods")) {
                    System.out.println("删除缓存----");
                    redisTemplate.opsForList().rightPop(key);
                }
            }
//            if(redisTemplate.hasKey("pageGoods+'*'")){
//                redisTemplate.opsForList().rightPop("pageGoods+'*'");
//            }
            //更新了商品信息 还要更新缓存  再去查询一次  把查询结果返回并放入redis中
            Goods noCacheGoods = findByIdNoCache(goods.getGoodsId());
            return noCacheGoods;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据主键查商品
     *
     * @param goodsId
     * @return
     */
    public Goods findByIdNoCache(Integer goodsId) {
        try {
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            return goods;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据指定的页数查询商品
     *
     * @param pageNum
     * @return
     */

    @Override
    public List<Goods> findBySpecifiedPage(Integer pageNum) {
        try {
            return goodsMapper.findBySpecifiedPage(pageNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 搜索商品
     *
     * @param goods
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Goods> searchGoods(Goods goods, Integer pageNum) {
        try {
            PageHelper.startPage(pageNum, PageUtil.searcheGoodsPageSie);
            List<Goods> goodsList = goodsMapper.searchGoods(goods);
            return new PageInfo<>(goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据二级分类id查询商品
     *
     * @param secondSortId
     * @return
     */
    @Override
    public PageInfo<Goods> findGoodsBySecondSortId(Integer secondSortId, Integer pageNum) {
        try {
            //需要远程调用分类码 不需要 需要分页吧
            PageHelper.startPage(pageNum, PageUtil.findGoodsBySecondSortIdSize);
            List<Goods> goodsList = goodsMapper.findGoodsBySecondSortId(secondSortId);
            return new PageInfo<>(goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据销量查询前几名的商品
     *
     * @return
     */
    @Override
    public List<Goods> findTopGoodsBySale() {
        try {
            return goodsMapper.findTopGoodsBySale();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询前几名的商品并存入redis缓存中
     *
     * @return
     */
    @Override
    public List<Object> findTopGoods() {
        List<Goods> topGoodsList = goodsMapper.findTopGoodsBySale();
        if (!StringUtils.isEmpty(topGoodsList)) {
            if (redisTemplate.hasKey("topGoods")) {
                Set topGoods = redisTemplate.opsForZSet().reverseRange("topGoods", 0, 5);
                //设置过期时间
//                Set<String> range=redisTemplate.opsForZSet().reverseRange("topGoods",0,5);
//                System.out.println("获取到的排行榜"+ JSONUtils.toJSONString(range));
                List<Object> goodsList = new ArrayList<>();
                topGoods.forEach(item -> {
                    goodsList.add(item);
                });
                return goodsList;
            } else {
                //排行榜在缓存中已经失效  再给它加进redis缓存中
                //单个新增or更新   Boolean add(K key, V value, double score);
                //批量新增or更新     Long add(K key, Set<TypedTuple<V>> tuples);
                //使用加法操作分        Double incrementScore(K key, V value, double delta);
               /* Set hashSet = new HashSet(topGoodsList);
                redisTemplate.opsForZSet().add("topGoods",hashSet);*/
                // Set<Goods> goods1 = new HashSet<>(topGoodsList);
                for (Goods goods : topGoodsList) {
                    redisTemplate.opsForZSet().add("topGoods", goods, goods.getSale());
                }
                redisTemplate.expire("topGoods", 30, TimeUnit.MINUTES);
                Set topGoods = redisTemplate.opsForZSet().range("topGoods", 0, 5);
                //转换
                List<Object> goodsList = new ArrayList<>();
                topGoods.forEach(item -> {
                    goodsList.add(item);
                });
                return goodsList;
            }
        }
        return null;
    }

    /**
     * 查询特价商品
     *
     * @return
     */
    @Override
    public List<Goods> findBargain() {
        try {
            return goodsMapper.findBargain();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 远程调用浏览信息服务的添加浏览记录的方法
     *
     * @param goods
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer addGoodsBrowseInfo(Goods goods, Integer userId) {
        // 这里goods 这里有goodsid 没有userid
        //远程调用添加用户的浏览记录
        System.out.println("准备去browse服务添加用户的浏览记录");
        Map s = browseClient.addBrowseInfo(goods.getGoodsName(), goods.getGoodsImage(), goods.getGoodsId(), userId);
       /* if(!StringUtils.isEmpty(s.get("browseFallbackInfo"))){
            return null;
            //熔断有问题 感觉不管我怎么弄 他好像就是会熔断  是时间设置的不好吗 还是啥
        }*/
        return 1;
    }

    @Override
    public PageInfo<Goods> findUsersByPage(Integer pageNum) {
        try {
            PageHelper.startPage(pageNum,PageUtil.goodsPageSize);
            List<Goods> usersList= goodsMapper.findUsersByPage();
            return new PageInfo<>(usersList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public List<Goods> searchGoods2(Goods goods) {
        try {
            return goodsMapper.searchGoods(goods);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Goods> selectTopGoods() {
        try {
            return goodsMapper.selectTopGoods();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public List<Goods> findGoodsBySecondSortIdNoPage(Integer secondSortId, Integer pageNum) {
        try {
            List<Goods> goodsList = goodsMapper.findGoodsBySecondSortId(secondSortId);
            return goodsList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Goods> selectTopGoods2() {
        try {
            return goodsMapper.selectTopGoods2();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 查询数据库商品的总记录数
     *
     * @return
     */
    @Override
    public Integer findRecordCount() {
        try {
            Integer i = goodsMapper.findRecordCount();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
