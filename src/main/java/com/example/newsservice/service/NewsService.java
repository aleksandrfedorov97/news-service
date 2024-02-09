package com.example.newsservice.service;

import com.example.newsservice.model.News;
import com.example.newsservice.web.model.dto.news.NewsFilter;

import java.util.List;

public interface NewsService {
    List<News> findAll();
    List<News> filterBy(NewsFilter newsFilter);
    News findById(Long id);
    News create(News news);
    News update(News news);
    void deleteById(Long id);
}
