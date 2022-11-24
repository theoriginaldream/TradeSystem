package com.example.dao;

import com.example.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper {

    int addShoppingCart(ShoppingCart shoppingCart);

    int deleteShoppingCart(@Param("shoppingcartid") int shoppingcartid);

    int deleteShoppingCartByItemId(@Param("itemid") int itemid);

    List<ShoppingCart> queryShoppingCartByUserId(@Param("userid") String userid);

    ShoppingCart queryShoppingCartById(@Param("shoppingcartid") int shoppingcartid);
}
