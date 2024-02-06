package com.example.newsservice.web.mapper;

import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsCategoryService;
import com.example.newsservice.service.UserService;
import com.example.newsservice.utils.AuthorizationUtils;
import com.example.newsservice.web.model.dto.news.NewsFindByIdResponse;
import com.example.newsservice.web.model.dto.news.NewsRequest;
import com.example.newsservice.web.model.dto.news.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;


public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsCategoryService newsCategoryService;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public News newsRequestToNews(NewsRequest newsRequest) {
        News news = new News();

        news.setTitle(newsRequest.getTitle());
        news.setBody(newsRequest.getBody());
        news.setAuthor(userService.findById(AuthorizationUtils.getCurrentUserId()));
        news.setCategory(newsCategoryService.findById(newsRequest.getNewsCategoryId()));
        news.setComments(null);

        return news;
    }

    @Override
    public News newsRequestToNews(Long newsId, NewsRequest newsRequest) {
        News news = newsRequestToNews(newsRequest);

        news.setId(newsId);

        return news;
    }

    @Override
    public NewsResponse newsToNewsResponse(News news) {
        NewsResponse newsResponse = new NewsResponse();

        newsResponse.setId(news.getId());
        newsResponse.setTitle(news.getTitle());
        newsResponse.setBody(news.getBody());
        newsResponse.setAuthorId(news.getAuthor().getId());
        newsResponse.setCategoryId(news.getCategory().getId());
        newsResponse.setCreateAt(news.getCreateAt());
        newsResponse.setCountComments(news.getComments().size());

        return newsResponse;
    }

    @Override
    public NewsFindByIdResponse newsToNewsFindByIdResponse(News news) {
        NewsFindByIdResponse newsFindByIdResponse = new NewsFindByIdResponse();

        newsFindByIdResponse.setId(news.getId());
        newsFindByIdResponse.setTitle(news.getTitle());
        newsFindByIdResponse.setBody(news.getBody());
        newsFindByIdResponse.setAuthorId(news.getAuthor().getId());
        newsFindByIdResponse.setCategoryId(news.getCategory().getId());
        newsFindByIdResponse.setCreateAt(news.getCreateAt());
        newsFindByIdResponse.setComments(news.getComments().stream()
                .map(commentMapper::commentToCommentResponse)
                .collect(Collectors.toList()));

        return newsFindByIdResponse;
    }
}
