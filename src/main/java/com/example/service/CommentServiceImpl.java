package com.example.service;

import com.example.dao.CommentMapper;
import com.example.pojo.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public int deleteComment(int commentid) {
        return commentMapper.deleteComment(commentid);
    }

    @Override
    public List<Comment> queryCommentByItemid(int itemid) {
        return commentMapper.queryCommentByItemid(itemid);
    }

    @Override
    public List<Comment> queryCommentByHost(String userid) {
        return commentMapper.queryCommentByHost(userid);
    }

    @Override
    public Comment queryCommentById(int commentid) {
        return commentMapper.queryCommentById(commentid);
    }

    @Override
    public List<Comment> queryCommentByItemHost(String userid) {
        return commentMapper.queryCommentByItemHost(userid);
    }

    @Override
    public List<Comment> queryCommentRequireByItemHost(String userid) {
        return commentMapper.queryCommentRequireByItemHost(userid);
    }
}
