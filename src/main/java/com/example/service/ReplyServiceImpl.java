package com.example.service;

import com.example.dao.ReplyMapper;
import com.example.pojo.Reply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Resource
    private ReplyMapper replyMapper;

    @Override
    public int addReply(Reply reply) {
        return replyMapper.addReply(reply);
    }

    @Override
    public int deleteReply(int replyid) {
        return replyMapper.deleteReply(replyid);
    }

    @Override
    public List<Reply> queryReplyByUserId(String userid) {
        return replyMapper.queryReplyByUserId(userid);
    }

    @Override
    public Reply queryReplyById(int replyid) {
        return replyMapper.queryReplyById(replyid);
    }

    @Override
    public List<Reply> queryReplyByConsultId(int consultid) {
        return replyMapper.queryReplyByConsultId(consultid);
    }

    @Override
    public List<Reply> queryAllReply() {
        return replyMapper.queryAllReply();
    }
}
