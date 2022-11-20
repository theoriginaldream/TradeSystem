package com.example.dao;

import com.example.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper {

    int addShoppingCart(ShoppingCart shoppingCart);

    int deleteShoppingCart(@Param("id") int id);

    List<ShoppingCart> queryShoppingCartByUserId(@Param("userid") String userid);

    ShoppingCart queryShoppingCartById(@Param("id") int id);
}
