package com.example.newsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {
    private Long id;
    private String title;
    private String body;
    private NewsCategory category;
    private User author;
    private Instant createAt;
    private List<Comment> comments;
}
