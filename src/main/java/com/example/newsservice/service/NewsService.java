package com.example.newsservice.service;

import com.example.newsservice.model.News;

import java.util.List;

public interface NewsService {
    List<News> findAll();
    News findById(Long id);
    News create(News news);
    News update(News news);
    void deleteById(Long id);
}
