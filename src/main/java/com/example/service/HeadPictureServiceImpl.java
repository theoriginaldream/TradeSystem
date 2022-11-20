package com.example.service;

import com.example.dao.HeadPictureMapper;
import com.example.pojo.HeadPicture;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HeadPictureServiceImpl implements HeadPictureService{

    @Resource
    private HeadPictureMapper headPictureMapper;

    @Override
    public int addHeadPicture(String userid, String headpicture) {
        return headPictureMapper.addHeadPicture(userid,headpicture);
    }

    @Override
    public int updateHeadPicture(String userid, String headpicture) {
        return headPictureMapper.updateHeadPicture(userid, headpicture);
    }

    @Override
    public HeadPicture queryHeadPicture(String userid) {
        return headPictureMapper.queryHeadPicture(userid);
    }
}
