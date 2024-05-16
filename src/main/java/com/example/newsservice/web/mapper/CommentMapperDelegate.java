package com.example.newsservice.web.mapper;

import com.example.newsservice.model.Comment;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.web.model.dto.comment.CommentCreateRequest;
import com.example.newsservice.web.model.dto.comment.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    NewsService newsService;

    @Override
    public Comment commentCreateRequestToComment(CommentCreateRequest commentCreateRequest) {
        Comment comment = new Comment();

        comment.setMessage(commentCreateRequest.getMessage());
        comment.setNews(newsService.findById(commentCreateRequest.getNewsId()));

        return comment;
    }
    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setMessage(comment.getMessage());
        commentResponse.setNewsId(comment.getNews().getId());
        commentResponse.setAuthorId(comment.getAuthor().getId());
        commentResponse.setCreateAt(comment.getCreateAt());

        return commentResponse;
    }
}
