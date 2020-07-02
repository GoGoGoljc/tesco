package com.zpark.goods.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zpark.goods.client.BrowseClient;
import com.zpark.goods.domain.Goods;
import com.zpark.goods.service.FileService;
import com.zpark.goods.service.GoodsService;
import com.zpark.goods.util.CheckUtil;
import com.zpark.goods.util.CommonUtil;
import com.zpark.goods.util.PageUtil;
import com.zpark.goods.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private BrowseClient browseClient;


    /**
     * 分页查询所有商品
     * 注意 暂时不需要库存信息 等到商品详情页再弄
     */
    @GetMapping("/findAllGoods/{pageNum}")
    public ResultObject findAllGoods(@PathVariable Integer pageNum) {
        ResultObject result = new ResultObject();
        try {
            //要校验码 页数码？
            Boolean aBoolean = CheckUtil.checkIntNum(pageNum);
            if (!aBoolean) {
                return new ResultObject(400, "哎呀！出问题了", null);
            }

            //多超页 给出相应的提示
            Integer totalRecord = goodsService.findRecordCount();
            int page = totalRecord / PageUtil.PageSize;
            if (totalRecord % PageUtil.PageSize != 0) {
                page++;
            }
            if (pageNum > page) {
                return new ResultObject(400, "该页没有商品了", null);
            }
//            PageHelper.startPage(pageNum,PageUtil.PageSize);
//            List<Goods> list= goodsService.findAllGoods(pageNum);
            //PageInfo<Goods> pageInfo= new PageInfo<>(list);
            PageInfo<Goods> pageInfo = goodsService.findAllGoods(pageNum);
            List<Goods> list = pageInfo.getList();
            for (Object goods : pageInfo.getList()) {
                System.out.println("分页查询出来的数据" + goods);   //一输出就发生异常
            }
            if (list != null && list.size() > 0) {
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(list);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询失败
        result.setCode(400);
        result.setMessage("抱歉，没有商品信息");
        return result;
    }

    /**
     * 查询最新商品
     * 暂时不需要库存信息 等到商品详情页再弄
     */

    @GetMapping("/findNewGoods")
    public ResultObject findNewGoods() {
        ResultObject<Object> result = new ResultObject<>();
        List<Goods> newGoodsList = goodsService.findNewGoods();
        if (newGoodsList != null && newGoodsList.size() > 0) {
            result.setCode(200);
            result.setMessage("查找成功");
            result.setResultData(newGoodsList);
            return result;
        }
        //查找不成功
        result.setCode(400);
        result.setMessage("抱歉，没有最新商品");
        return result;
    }

    /**
     * 查询特价商品
     */
    @GetMapping("/findBargain")
    public ResultObject findBargain() {
        try {
            List<Goods> goodsList = goodsService.findBargain();
            if (goodsList != null && goodsList.size() > 0) {
                return new ResultObject(200, "查询成功", goodsList);
            }
            return new ResultObject(400, "查询失败", goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询最热商品 findHotGoods
     */

    @GetMapping("/findHotGoods")
    public ResultObject findHotGoods() {
        ResultObject<Object> result = new ResultObject<>();
        List<Goods> hotGoodsList = goodsService.findHotGoods();
        if (hotGoodsList != null && hotGoodsList.size() > 0) {
            result.setCode(200);
            result.setMessage("查找成功");
            result.setResultData(hotGoodsList);
            return result;
        }
        //查找不成功
        result.setCode(400);
        result.setMessage("抱歉，没有最热商品");
        return result;
    }

    /**
     * 根据id查询商品 findById  商品详情页
     * 需要远程调用stock-service模块 看看库存信息  no
     */

    @GetMapping("/findById")
    public ResultObject findById(@RequestParam("goods_id") Integer goodsId, HttpServletRequest request) {
        ResultObject<Object> result = new ResultObject<>();
        try {

            Goods goods = goodsService.findById(goodsId);
            System.out.println("调用了my-goods-service的findById方法" + goods);
            if (goods != null && !goods.equals("")) {
                //这个验证有问题吗
                //查看商品详情的时候 添加浏览信息表的记录
                String token = request.getHeader("token");
                //从redis中根据token获取用户id信息等
                // 不判断码
                Integer userId = (Integer) redisTemplate.opsForHash().get("userToken:" + token, "userId");
                if (!CheckUtil.checkIntNum(userId)) {
                    //用户id为空
                    System.out.println("=======");
                    return new ResultObject(200, "查找成功", goods);
                }

                //用户id不为空就添加商品的浏览信息记录
                Map msg = browseClient.addBrowseInfo(goods.getGoodsName(), goods.getGoodsImage(), goods.getGoodsId(), userId);

                if (!StringUtils.isEmpty(msg.get("browseFallbackInfo"))) {
                    //进行服务降级  添加商品的浏览信息记录 失败  那边有事务回滚  说不定都没到那就降级了
                    return new ResultObject(400, "网络异常", null);
                }
                return new ResultObject(200, "查找成功", goods);
            }

            //没有查找到该商品的信息
            result.setCode(400);
            result.setMessage("抱歉，没有该商品信息");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 管理员查询商品id
     *
     * @param goodsId
     * @param request
     * @return
     */

    @PostMapping("/adminFindById")
    public ResultObject adminFindById(@RequestParam("goods_id") Integer goodsId, HttpServletRequest request) {
        ResultObject<Object> result = new ResultObject<>();
        try {
            Goods goods = goodsService.findById(goodsId);
            System.out.println("调用了my-goods-service的findById方法" + goods);
            if (goods != null && !goods.equals("")) {
                //查看商品详情的时候 这里不添加浏览信息表的记录
                return new ResultObject(200, "查找成功", goods);
            }

            //没有查找到该商品的信息
            result.setCode(400);
            result.setMessage("抱歉，没有该商品信息");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
     * 根据id查询商品 findById  商品详情页 但这里多传了个登录用户id(上面修改的那个根据商品id查询商品好像已经可以 ，这个感觉就可以不需要它了)
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/findByIds")
    public ResultObject findByIds(@RequestParam("goods_id") Integer goodsId, @RequestParam("user_id") Integer userId) {
        try {
            Goods goods = goodsService.findById(goodsId);
            System.out.println("调用了my-goods-service的findByIds方法" + goods);
            if (goods != null && !goods.equals("")) {
                //这个验证有问题吗
                //查看商品详情的时候 添加浏览信息表的记录
                Integer i = goodsService.addGoodsBrowseInfo(goods, userId);
                if (i > 0) {
                    return new ResultObject(200, "查找成功", goods);
                }
                return new ResultObject(400, "查找商品的途中遇到其他的异常", null);
            }
            //没有查找到该商品的信息
            return new ResultObject(400, "抱歉，没有该商品信息", null);


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * addGoods 添加商品
     * 添加上商品的时候可以把关联的其他的表的数据给填写上
     */
    @PostMapping("/addGoods")
    public ResultObject addGoods(Goods goods) {
        ResultObject result = new ResultObject();
        try {
            //校验一下商品的信息 前台校验不好吗 校验啥呢
            Boolean aBoolean = CheckUtil.checkGoodsAttribute(goods);
            if (!aBoolean) {
                return new ResultObject(400, "你填写的信息不全哦", null);
            }
            goods.setGoodsAddTime(new Date());
            Integer i = goodsService.addGoods(goods);
            if (i > 0) {
                result.setCode(200);
                result.setMessage("添加成功");
                return result;
            }
            result.setCode(400);
            result.setMessage("添加失败");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * deleteById
     * 根据id删除商品
     * 删除商品 需要删除图片服务的信息  库存呢 要删不 可以不删
     */

    @PostMapping("/deleteById")
    public ResultObject deleteById(@RequestParam("goods_id") Integer goodsId) {
        try {
            System.out.println("---------------删除商品------");
            ResultObject result = new ResultObject();
            if (StringUtils.isEmpty(goodsId)) {
                return new ResultObject(400, "不好意思,服务器故障", null);
            }
            Integer i = goodsService.deleteById(goodsId);
            if (i > 0) {
                result.setCode(200);
                result.setMessage("删除商品成功");
                result.setResultData(null);
                return result;
            }
            result.setCode(400);
            result.setMessage("删除商品失败");
            result.setResultData(null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * updateById  根据商品id 更新商品的数据
     * 看看需要远程调用image-service服务 库存服务  修改信息
     */
    @PostMapping("/updateById")
    public ResultObject updateById(Goods goods) {
        try {
            System.out.println("根据商品id 更新商品的数据");
            ResultObject<Object> result = new ResultObject<>();
            if (StringUtils.isEmpty(goods.getGoodsId())) {
                return new ResultObject(400, "你填写的信息不全面哦", null);
            }
            Boolean aBoolean = CheckUtil.checkGoodsAttribute2(goods);
            if (!aBoolean) {
                return new ResultObject(400, "你填写的信息不全面哦2", null);
            }

            Goods goods1 = goodsService.updateById(goods);
            if (!StringUtils.isEmpty(goods1)) {
                result.setCode(200);
                result.setMessage("更新成功");
                result.setResultData(goods1);
                return result;
            }

            result.setCode(400);
            result.setMessage("更新失败");
            result.setResultData(null);
            return result;
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
    @PostMapping("/searchGoods/{pageNum}")
    public ResultObject searchGoods(@RequestBody Goods goods, @PathVariable("pageNum") Integer pageNum) {
        try {
            System.out.println("进行模糊查询");
            ResultObject result = new ResultObject();
            Boolean aBoolean = CheckUtil.checkIntNum(pageNum);
            if (!aBoolean) {
                return new ResultObject(400, "出现问题啦", null);
            }
            //检验商品的信息是否合法
            //也要分页显示
            PageInfo<Goods> goodsList = goodsService.searchGoods(goods, pageNum);
            List<Goods> list = goodsList.getList();
            if (list != null && list.size() > 0) {
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(goodsList);
                return result;
            }
            result.setCode(400);
            result.setMessage("没有查询到数据");
            result.setResultData(null);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @PostMapping("/searchGoods2")
    public ResultObject searchGoods2(Goods goods) {
        try {
            System.out.println("进行模糊查询2");
            ResultObject result = new ResultObject();
            List<Goods> list =goodsService.searchGoods2(goods);
            if (list != null && list.size() > 0) {
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(list);
                return result;
            }
            result.setCode(400);
            result.setMessage("没有查询到数据");
            result.setResultData(null);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据二级分类id查询商品
     */

    @GetMapping("/findGoodsBySecondSortId")
    public ResultObject findGoodsBySecondSortId(@RequestParam("second_sort_id") Integer secondSortId, @RequestParam("pageNum") Integer pageNum) {
        ResultObject result = new ResultObject();
        try {
            if (StringUtils.isEmpty(secondSortId)) {
                return new ResultObject(400, "网路不好了啦,请稍候重试", null);
            }
            Boolean aBoolean = CheckUtil.checkIntNum(pageNum);
            if (!aBoolean) {
                return new ResultObject(400, "网路不好了啦,请稍候重试", null);
            }
            PageInfo<Goods> goodsList = goodsService.findGoodsBySecondSortId(secondSortId, pageNum);
            List<Goods> list = goodsList.getList();
            if (list != null && list.size() > 0) {
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(goodsList);
                return result;
            }
            result.setCode(400);
            result.setMessage("查询失败");
            result.setResultData(null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PostMapping("/findByPage")
    public ResultObject findByPage(@RequestParam("pageNum") Integer pageNum) {
        try {
            System.out.println("分页查询商品");
            if (!CheckUtil.checkIntNum(pageNum)) {
                return new ResultObject(400, "服务器繁,请稍候再试", null);
            }
            PageInfo<Goods> page = goodsService.findUsersByPage(pageNum);
            List<Goods> list = page.getList();
            if (list != null && list.size() > 0) {
                return new ResultObject(200, "查询成功", page);
            }
            return new ResultObject(400, "查询失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询销量前几名商品
     * @return
     */

    @GetMapping("/selectTopGoods")
    public ResultObject selectTopGoods(){
        try {
            List<Goods> goodsList = goodsService.selectTopGoods();
            if (goodsList != null && goodsList.size() > 0) {
                return new ResultObject(200, "查询成功", goodsList);
            }
            return new ResultObject(400, "查询失败", goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据二级分类查询商品（不分页）
     */


    @GetMapping("/findGoodsBySecondSortIdNoPage")
    public ResultObject findGoodsBySecondSortIdNoPage(@RequestParam("second_sort_id") Integer secondSortId, @RequestParam("pageNum") Integer pageNum) {
        ResultObject result = new ResultObject();
        try {
            if (StringUtils.isEmpty(secondSortId)) {
                return new ResultObject(400, "网路不好了啦,请稍候重试", null);
            }
            Boolean aBoolean = CheckUtil.checkIntNum(pageNum);
            if (!aBoolean) {
                return new ResultObject(400, "网路不好了啦,请稍候重试", null);
            }
            List<Goods> goodsList = goodsService.findGoodsBySecondSortIdNoPage(secondSortId, pageNum);

            if (goodsList != null && goodsList.size() > 0) {
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(goodsList);
                return result;
            }
            result.setCode(400);
            result.setMessage("查询失败");
            result.setResultData(null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @GetMapping("/selectTopGoods2")
    public ResultObject selectTopGoods2(){
        try {
            List<Goods> goodsList = goodsService.selectTopGoods2();
            if (goodsList != null && goodsList.size() > 0) {
                return new ResultObject(200, "查询成功", goodsList);
            }
            return new ResultObject(400, "查询失败", goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



}
