package com.example.newsservice.web.model.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {
    private String title;
    private String body;
    private Long newsCategoryId;
}
