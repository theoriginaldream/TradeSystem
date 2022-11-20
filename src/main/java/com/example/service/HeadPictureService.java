package com.example.service;

import com.example.pojo.HeadPicture;

public interface HeadPictureService {
    int addHeadPicture(String userid,String headpicture);

    int updateHeadPicture(String userid,String headpicture);

    HeadPicture queryHeadPicture(String userid);
}
