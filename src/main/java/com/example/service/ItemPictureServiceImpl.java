package com.example.service;

import com.example.dao.ItemPictureMapper;
import com.example.pojo.ItemPicture;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ItemPictureServiceImpl implements ItemPictureService{

    @Resource
    private ItemPictureMapper itemPictureMapper;

    @Override
    public int addItemPicture(ItemPicture itempicture) {
        return itemPictureMapper.addItemPicture(itempicture);
    }

    @Override
    public int deleteItemPicture(int itemid) {
        return itemPictureMapper.deleteItemPicture(itemid);
    }

    @Override
    public int updateItemPicture(ItemPicture itempicture) {
        return itemPictureMapper.updateItemPicture(itempicture);
    }

    @Override
    public ItemPicture queryItemPicture(int itemid) {
        return itemPictureMapper.queryItemPicture(itemid);
    }
}
