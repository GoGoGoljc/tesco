package com.legou.cart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.cart.client.GoodsClient;
import com.legou.cart.client.UserClient;
import com.legou.cart.domain.Cart;
import com.legou.cart.mapper.CartMapper;
import com.legou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author SJXY
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private GoodsClient goodsClient;
    /**
     * 根据用户id查询购物车列表项
     * @param userId
     * @return
     */
    @Override
    public List<Cart> findByUserId(Integer userId) {


        List<Cart> shopcarts = cartMapper.selectByUserId(userId);
        for (Cart item : shopcarts) {
            if(!StringUtils.isEmpty(item)){
                System.out.println(item);
            }
        }
        return shopcarts;
    }

    @Override
    public Integer deleteByPrimaryKey(Integer shopitemId) {
        Integer i = cartMapper.deleteByPrimaryKey(shopitemId);
        System.out.println(i);
        return i;
    }

    @Override
    public int insert(Cart cart) {
            return cartMapper.add(cart);
    }

    @Override
    public Cart goodExist(Integer userId,Integer goodId) {
        System.out.println(cartMapper.goodIdExist(userId,goodId));
        return  cartMapper.goodIdExist(userId,goodId);
    }

    @Override
    public int updateNum(Integer goodId) {
        return cartMapper.updateNum(goodId);
    }

    @Override
    public PageInfo<Cart> selectPages(Cart cart, Integer pageNum) {
        PageHelper.startPage(pageNum, 6);
        List<Cart> cartsPage=this.cartMapper.selectPages(cart.getUserId());
        return new PageInfo<>(cartsPage);
    }

    /**
     * 支付成功清空购物车
     * @param userId
     * @return
     */
    @Override
    public Integer deleteByUserId(Integer userId) {

        return cartMapper.deleteByUserId(userId);
    }
}
