package com.example.newsservice.service;

import com.example.newsservice.model.NewsCategory;

import java.util.List;

public interface NewsCategoryService {
    List<NewsCategory> findAll();
    NewsCategory findById(Long id);
    NewsCategory create(NewsCategory newsCategory);
    NewsCategory update(NewsCategory newsCategory);
    void deleteById(Long id);
}
