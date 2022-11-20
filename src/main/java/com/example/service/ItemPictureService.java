package com.example.service;

import com.example.pojo.ItemPicture;
import org.apache.ibatis.annotations.Param;

public interface ItemPictureService {

    int addItemPicture(ItemPicture itemPicture);

    int deleteItemPicture(int itemid);

    int updateItemPicture(ItemPicture itempicture);

    ItemPicture queryItemPicture(int itemid);

}
