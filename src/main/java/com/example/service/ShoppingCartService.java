package com.example.service;

import com.example.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    int addShoppingCart(ShoppingCart shoppingCart);

    int deleteShoppingCart(int id);

    List<ShoppingCart> queryShoppingCartByUserId(String userid);

    ShoppingCart queryShoppingCartById(int id);
}
