package com.legou.cart.mapper;

import com.legou.cart.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @author SJXY
 */
@Mapper
@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer shopitemId);

    List<Cart> selectByUserId(Integer userId);
    int insert(Cart cart);
    Cart goodIdExist(Integer userId,Integer goodId);
    int updateNum(Integer goodId);

    int add(Cart cart);

    //     分页查询管理员信息


    List<Cart> selectPages(Integer userId);
    int deleteByUserId(Integer userId);


}
