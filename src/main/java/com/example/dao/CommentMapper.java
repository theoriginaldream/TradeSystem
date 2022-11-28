package com.example.dao;

import com.example.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int addComment(Comment comment);

    int deleteComment(@Param("commentid") int commentid);

    List<Comment> queryCommentByItemid(@Param("itemid") int itemid);

    List<Comment> queryCommentByHost(@Param("userid") String userid);

    Comment queryCommentById(@Param("commentid") int commentid);

    List<Comment> queryCommentByItemHost(@Param("userid") String userid);

    List<Comment> queryCommentRequireByItemHost(@Param("userid") String userid);
}
