package com.example.dao;

import com.example.pojo.HeadPicture;
import org.apache.ibatis.annotations.Param;

public interface HeadPictureMapper {

    int addHeadPicture(@Param("userid") String userid,@Param("headpicture") String headpicture);

    int updateHeadPicture(@Param("userid") String userid,@Param("headpicture") String headpicture);

    HeadPicture queryHeadPicture(@Param("userid") String userid);

}
