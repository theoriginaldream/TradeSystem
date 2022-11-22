package com.example.dao;

import com.example.pojo.Consult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsultMapper {
    int addConsult(Consult consult);

    int deleteConsult(@Param("consultid") int consultid);

    List<Consult> queryAllConsult();
}
