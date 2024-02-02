package com.example.newsservice.repository;

import com.example.newsservice.model.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
}
