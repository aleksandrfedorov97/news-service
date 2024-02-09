package com.example.newsservice.web.model.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private Long authorId;
    private Long newsCategoryId;
}
