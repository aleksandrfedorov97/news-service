package com.example.newsservice.repository;

import com.example.newsservice.model.News;
import com.example.newsservice.web.model.dto.news.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byNewsCategoryId(newsFilter.getNewsCategoryId()))
                .and(byAuthorId(newsFilter.getAuthorId()));
    }

    static Specification<News> byNewsCategoryId(Long newsCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (newsCategoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get("id"), newsCategoryId);
        };
    }


    static Specification<News> byAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("author").get("id"), authorId);
        };
    }
}