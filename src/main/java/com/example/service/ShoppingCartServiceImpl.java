package com.example.service;

import com.example.dao.ShoppingCartMapper;
import com.example.pojo.ShoppingCart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public int addShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartMapper.addShoppingCart(shoppingCart);
    }

    @Override
    public int deleteShoppingCart(int shoppingcartid) {
        return shoppingCartMapper.deleteShoppingCart(shoppingcartid);
    }

    @Override
    public int deleteShoppingCartByItemId(int itemid) {
        return shoppingCartMapper.deleteShoppingCartByItemId(itemid);
    }

    @Override
    public List<ShoppingCart> queryShoppingCartByUserId(String userid) {
        return shoppingCartMapper.queryShoppingCartByUserId(userid);
    }

    @Override
    public ShoppingCart queryShoppingCartById(int shoppingcartid) {
        return shoppingCartMapper.queryShoppingCartById(shoppingcartid);
    }
}
