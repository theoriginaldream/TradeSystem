package com.example.service;

import com.example.pojo.Item;

import java.util.List;

public interface ItemService {
    //增加商品
    int addItem(Item item);

    // 删除商品
    int deleteItem(int itemid);

    // 修改商品
    int updateItem(Item item);

    // 全称查询商品
    List<Item> queryItemByAllName(String itemname);

    // 模糊查询商品
    List<Item> queryItemByName(String itemname);

    // 按类型搜索商品
    List<Item> queryItemByType(String schoolzone);

    // 搜索全部商品
    List<Item> queryAllItem();

    // 根据id查询商品
    Item queryItemById(int itemid);

    // 查询用户发布的
    List<Item> queryItemByHost(String host);
}
