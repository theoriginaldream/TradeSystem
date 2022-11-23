package com.example.service;

import com.example.pojo.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyService {
    int addReply(Reply reply);

    int deleteReply(int replyid);

    List<Reply> queryReplyByUserId(String userid);

    Reply queryReplyById(int replyid);

    List<Reply> queryReplyByConsultId(int consultid);

    List<Reply> queryAllReply();
}
