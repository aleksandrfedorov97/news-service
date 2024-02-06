package com.example.newsservice.service;

import com.example.newsservice.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    List<Comment> findAllByNewsId(Long newsId);
    Comment findById(Long id);
    Comment create(Comment comment);
    Comment update(Comment comment);
    void deleteById(Long id);
}
