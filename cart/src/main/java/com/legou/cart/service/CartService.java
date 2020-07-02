package com.legou.cart.service;

import com.github.pagehelper.PageInfo;
import com.legou.cart.domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findByUserId(Integer userId);

    Integer deleteByPrimaryKey(Integer shopitemId);

    int insert(Cart cart);

    Cart goodExist(Integer userId,Integer goodId);

    int updateNum(Integer goodId);

    PageInfo<Cart> selectPages(Cart cart, Integer pageNum);

    Integer deleteByUserId(Integer userId);






}
