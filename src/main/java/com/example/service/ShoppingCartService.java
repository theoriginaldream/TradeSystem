package com.example.service;

import com.example.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    int addShoppingCart(ShoppingCart shoppingCart);

    int deleteShoppingCart(int shoppingcartid);

    int deleteShoppingCartByItemId(int itemid);

    List<ShoppingCart> queryShoppingCartByUserId(String userid);

    ShoppingCart queryShoppingCartById(int shoppingcartid);
}
