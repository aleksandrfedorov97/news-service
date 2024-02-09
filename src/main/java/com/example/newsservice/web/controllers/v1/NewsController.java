package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.aop.OwnerRestriction;
import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.web.mapper.NewsMapper;
import com.example.newsservice.web.model.dto.news.NewsFilter;
import com.example.newsservice.web.model.dto.news.NewsFindByIdResponse;
import com.example.newsservice.web.model.dto.news.NewsListResponse;
import com.example.newsservice.web.model.dto.news.NewsRequest;
import com.example.newsservice.web.model.dto.news.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter newsFilter) {

        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.filterBy(newsFilter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFindByIdResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                newsMapper.newsToNewsFindByIdResponse(
                        newsService.findById(id)
                )
        );
    }


    @PostMapping
    public ResponseEntity<NewsFindByIdResponse> create(@RequestBody NewsRequest newsRequest) {
        News newNews = newsService.create(newsMapper.newsRequestToNews(newsRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        newsMapper.newsToNewsFindByIdResponse(newNews)
                );
    }

    @OwnerRestriction
    @PutMapping("/{id}")
    public ResponseEntity<NewsFindByIdResponse> update(@PathVariable Long id, @RequestBody NewsRequest newsRequest) {
        News updatedNews = newsService.update(newsMapper.newsRequestToNews(id, newsRequest));

        return ResponseEntity.ok(
                newsMapper.newsToNewsFindByIdResponse(updatedNews)
        );
    }

    @OwnerRestriction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newsService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
