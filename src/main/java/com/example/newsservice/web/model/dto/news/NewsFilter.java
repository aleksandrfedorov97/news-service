package com.example.newsservice.web.model.dto.news;

import com.example.newsservice.validation.NewsFilterValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NewsFilterValid
public class NewsFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private Long authorId;
    private Long newsCategoryId;
}
