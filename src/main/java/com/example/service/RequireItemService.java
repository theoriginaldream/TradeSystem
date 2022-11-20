package com.example.service;

import com.example.pojo.RequireItem;

import java.util.List;

public interface RequireItemService {

    int addRequireItem(RequireItem requireItem);

    int deleteRequireItem(int ritemid);

    int updateRequireItem(RequireItem requireItem);

    List<RequireItem> queryRequireItemByName(String ritemname);

    List<RequireItem> queryRequireItemByHost(String host);

    List<RequireItem> queryAllRequireItem();

    RequireItem queryRequireItemById(int ritemid);

}
