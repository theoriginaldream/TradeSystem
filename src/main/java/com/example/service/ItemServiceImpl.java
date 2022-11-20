package com.example.service;

import com.example.dao.ItemMapper;
import com.example.pojo.Item;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Resource
    private ItemMapper itemMapper;

    @Override
    public int addItem(Item item) {
        return itemMapper.addItem(item);
    }

    @Override
    public int deleteItem(int itemid) {
        return itemMapper.deleteItem(itemid);
    }

    @Override
    public int updateItem(Item item) {
        return itemMapper.updateItem(item);
    }

    @Override
    public List<Item> queryItemByAllName(String itemname) {
        return itemMapper.queryItemByAllName(itemname);
    }

    @Override
    public List<Item> queryItemByName(String itemname) {
        return itemMapper.queryItemByName(itemname);
    }

    @Override
    public List<Item> queryItemByType(String schoolzone) {
        return itemMapper.queryItemByType(schoolzone);
    }

    @Override
    public List<Item> queryAllItem() {
        return itemMapper.queryAllItem();
    }

    @Override
    public Item queryItemById(int itemid) {
        return itemMapper.queryItemById(itemid);
    }

    @Override
    public List<Item> queryItemByHost(String host) {
        return itemMapper.queryItemByHost(host);
    }
}
