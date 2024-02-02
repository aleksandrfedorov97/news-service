package com.example.newsservice.web.model.dto.news;

import com.example.newsservice.model.Comment;
import com.example.newsservice.web.model.dto.comment.CommentListResponse;
import com.example.newsservice.web.model.dto.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsFindByIdResponse {
    private Long Id;
    private String title;
    private String body;
    private Long authorId;
    private Long categoryId;
    private Instant createAt;
    private List<CommentResponse> comments;
}
