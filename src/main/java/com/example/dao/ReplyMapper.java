package com.example.dao;

import com.example.pojo.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {

    int addReply(Reply reply);

    int deleteReply(@Param("replyid") int replyid);

    List<Reply> queryReplyByUserId(@Param("userid") String userid);

    Reply queryReplyById(@Param("replyid") int replyid);

    List<Reply> queryReplyByConsultId(@Param("consultid") int consultid);

    List<Reply> queryAllReply();

}
