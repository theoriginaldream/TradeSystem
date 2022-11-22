package com.example.service;

import com.example.dao.ConsultMapper;
import com.example.pojo.Consult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConsultServiceImpl implements ConsultService{

    @Resource
    private ConsultMapper consultMapper;

    @Override
    public int addConsult(Consult consult) {
        return consultMapper.addConsult(consult);
    }

    @Override
    public int deleteConsult(int consultid) {
        return consultMapper.deleteConsult(consultid);
    }

    @Override
    public List<Consult> queryAllConsult() {
        return consultMapper.queryAllConsult();
    }
}
