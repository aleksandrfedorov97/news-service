package com.example.newsservice.web.mapper;

import com.example.newsservice.model.News;
import com.example.newsservice.web.model.dto.news.NewsFindByIdResponse;
import com.example.newsservice.web.model.dto.news.NewsListResponse;
import com.example.newsservice.web.model.dto.news.NewsRequest;
import com.example.newsservice.web.model.dto.news.NewsResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    News newsRequestToNews(NewsRequest newsRequest);
    News newsRequestToNews(Long newsId, NewsRequest newsRequest);

    NewsResponse newsToNewsResponse(News news);
    NewsFindByIdResponse newsToNewsFindByIdResponse(News news);

    default NewsListResponse newsListToNewsListResponse(List<News> news) {
        NewsListResponse newsListResponse = new NewsListResponse();

        newsListResponse.setNews(
                news.stream()
                        .map(this::newsToNewsResponse)
                        .collect(Collectors.toList())
        );

        return newsListResponse;
    }
}
