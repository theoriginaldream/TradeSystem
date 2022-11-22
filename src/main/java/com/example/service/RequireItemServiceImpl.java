package com.example.service;

import com.example.dao.RequireItemMapper;
import com.example.pojo.RequireItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RequireItemServiceImpl implements RequireItemService{

    @Resource
    private RequireItemMapper requireItemMapper;

    @Override
    public int addRequireItem(RequireItem requireItem) {
        return requireItemMapper.addRequireItem(requireItem);
    }

    @Override
    public int deleteRequireItem(int ritemid) {
        return requireItemMapper.deleteRequireItem(ritemid);
    }

    @Override
    public int updateRequireItem(RequireItem requireItem) {
        return requireItemMapper.updateRequireItem(requireItem);
    }

    @Override
    public List<RequireItem> queryRequireItemByName(String ritemname) {
        return requireItemMapper.queryRequireItemByName(ritemname);
    }

    @Override
    public List<RequireItem> queryRequireItemByHost(String host) {
        return requireItemMapper.queryRequireItemByHost(host);
    }

    @Override
    public List<RequireItem> queryRequireItemByZone(String schoolzone) {
        return requireItemMapper.queryRequireItemByZone(schoolzone);
    }

    @Override
    public List<RequireItem> queryAllRequireItem() {
        return requireItemMapper.queryAllRequireItem();
    }

    @Override
    public RequireItem queryRequireItemById(int ritemid) {
        return requireItemMapper.queryRequireItemById(ritemid);
    }
}
