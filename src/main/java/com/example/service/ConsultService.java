package com.example.service;

import com.example.pojo.Consult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsultService {
    int addConsult(Consult consult);

    int deleteConsult(int consultid);

    List<Consult> queryAllConsult();

    Consult queryConsultById(int consultid);

    List<Consult> queryConsultByUserId(String userid);

}
