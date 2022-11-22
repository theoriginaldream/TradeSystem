package com.example.dao;

import com.example.pojo.RequireItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequireItemMapper {
    int addRequireItem(RequireItem requireItem);

    int deleteRequireItem(@Param("ritemid") int ritemid);

    int updateRequireItem(RequireItem requireItem);

    List<RequireItem> queryRequireItemByName(@Param("ritemname") String ritemname);

    List<RequireItem> queryRequireItemByHost(@Param("host") String host);

    List<RequireItem> queryRequireItemByZone(@Param("schoolzone") String schoolzone);

    List<RequireItem> queryAllRequireItem();

    RequireItem queryRequireItemById(@Param("ritemid") int ritemid);
}
