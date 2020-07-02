package com.legou.cart.controller;
import com.github.pagehelper.PageInfo;
import com.legou.cart.client.GoodsClient;
import com.legou.cart.domain.Cart;
import com.legou.cart.service.CartService;
import com.legou.cart.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author SJXY
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {


    @Autowired
    private CartService cartService;
    @Resource
    private GoodsClient goodsClient;

    /**
     * 远程拿到商品加入购物车
     * @param goodId
     * @return
     */
    @GetMapping("/addCart")
        public ResultObject findByGoodId(@RequestParam("userId") Integer userId,@RequestParam("goodId") Integer goodId){
        System.out.println("访问了addCart方法");
        Map goods= goodsClient.findById(goodId);
        Map good= (Map) goods.get("resultData");
        System.out.println("远程拿到的是"+good);
        if(!StringUtils.isEmpty(cartService.goodExist(userId,goodId))){
            System.out.println("购物车已存在此商品");
            cartService.updateNum(goodId);
            return new ResultObject(200, "购物车已存在此商品，数量加一", good);
        }else {
            String goodName = (String) good.get("goodsName");
            Double goodPrice = (Double) good.get("goodsPrice");
            Double goodDiscount = (Double) good.get("goodsDiscount");
            Integer goodNum = (Integer) good.get("goodsNum");
            String goodImage= (String) good.get("goodsImage");
            Cart cart=new Cart();
            cart.setGoodName(goodName);
            cart.setGoodPrice(goodPrice);
            cart.setGoodDiscount(goodDiscount);
            cart.setGoodNum(goodNum);
            cart.setGoodImage(goodImage);
            cart.setGoodId(goodId);
            cart.setUserId(userId);
            cart.setGoodTotal(goodDiscount*goodNum);
            System.out.println(cart);
            cartService.insert(cart);
            return  new ResultObject(200,"将远程拿到的数据插入了数据库",cart);
        }


    }


    /**
     * 购物车列表
     */
    @GetMapping("/getList")
    public ResultObject findByUserId(@RequestParam("userId") Integer userId, HttpServletResponse response) {
    //跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        System.out.println("访问了getList方法");
        List<Cart> shopcard = cartService.findByUserId(userId);
        if (shopcard != null && shopcard.size() > 0) {
            return new ResultObject(200, "购物车有商品", shopcard);
        }
        System.out.println("远程拿到的东西:"+shopcard);
        return new ResultObject(400, "查询失败,暂无商品", null);
    }


    //分页查询
    @RequestMapping("selectList/{pageNum}")
    @ResponseBody

    public ResultObject selectList(@PathVariable("pageNum") Integer pageNum,Cart cart){
        PageInfo<Cart> pageInfo = this.cartService.selectPages(cart, pageNum);
        List<Cart> list = pageInfo.getList();
        if (list != null && list.size() > 0) {

            return new ResultObject(200,"查询成功",pageInfo);

        }
        return new ResultObject(400,"查询失败",null);
    }




    /**
     * 根据商品编号删除商品
     *
     * @param shopItemId
     * @return
     */

    @GetMapping("/deleteGood")

    public ResultObject deleteByShopItemId(@RequestParam("goodId") Integer shopItemId,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        System.out.println("访问了deleteByShopItemId方法");
        ResultObject result = new ResultObject();
        Integer i = cartService.deleteByPrimaryKey(shopItemId);
        if (i > 0) {
            result.setCode(200);
            result.setMessage("删除购物车成功！");
            result.setResultData(null);
            return result;
        }
        result.setCode(400);
        result.setMessage("删除购物车失败！,该商品不存在!");
        result.setResultData(null);
        return result;
    }




    @PostMapping("/updateGoodNum")
    public ResultObject updateNum(Integer goodId) {
        ResultObject result = new ResultObject<>();
        int m = cartService.updateNum(goodId);
        try {
            if (m > 0) {
                return new ResultObject(200,"数量加一成功",1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObject(400,"数量加一失败",0);
    }

    @PostMapping("/deleteByUserId")
    public ResultObject deleteByuserId(@RequestParam("userId") Integer userId){
        int i=cartService.deleteByUserId(userId);
        if(i>0){
            return new ResultObject(200,"清空购物车成功",1);
        }else {
            return new ResultObject(400,"清空购物车失败",0);
        }
    }


}
