package com.example.newsservice.web.model.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private Long Id;
    private String title;
    private String body;
    private Long authorId;
    private Long categoryId;
    private Instant createAt;
    private Integer countComments;
}
