package com.example.newsservice.repository;

import com.example.newsservice.model.NewsСategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsСategory, Long> {
}
