package com.example.newsservice.repository;

import com.example.newsservice.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
