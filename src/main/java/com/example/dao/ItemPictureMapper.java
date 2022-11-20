package com.example.dao;

import com.example.pojo.ItemPicture;
import org.apache.ibatis.annotations.Param;

public interface ItemPictureMapper {

    int addItemPicture(ItemPicture itempicture);

    int deleteItemPicture(@Param("itemid") int itemid);

    int updateItemPicture(ItemPicture itempicture);

    ItemPicture queryItemPicture(@Param("itemid") int itemid);

}
