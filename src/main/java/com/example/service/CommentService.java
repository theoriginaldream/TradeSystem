package com.example.service;

import com.example.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    int deleteComment(int commentid);

    List<Comment> queryCommentByItemid(int itemid);

    List<Comment> queryCommentByHost(String userid);

    Comment queryCommentById(int commentid);

    List<Comment> queryCommentByItemHost(String userid);
}
