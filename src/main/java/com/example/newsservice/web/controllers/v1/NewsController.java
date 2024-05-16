package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.aop.news.NewsOwnerRestriction;
import com.example.newsservice.model.News;
import com.example.newsservice.model.user.RoleType;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.web.mapper.NewsMapper;
import com.example.newsservice.web.model.dto.news.NewsFilter;
import com.example.newsservice.web.model.dto.news.NewsFindByIdResponse;
import com.example.newsservice.web.model.dto.news.NewsListResponse;
import com.example.newsservice.web.model.dto.news.NewsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<NewsListResponse> findAll(@Valid NewsFilter newsFilter) {

        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.filterBy(newsFilter)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsFindByIdResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                newsMapper.newsToNewsFindByIdResponse(
                        newsService.findById(id)
                )
        );
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsFindByIdResponse> create(@RequestBody NewsRequest newsRequest) {
        News newNews = newsService.create(newsMapper.newsRequestToNews(newsRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        newsMapper.newsToNewsFindByIdResponse(newNews)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @NewsOwnerRestriction({RoleType.ROLE_ADMIN, RoleType.ROLE_USER, RoleType.ROLE_MODERATOR})
    public ResponseEntity<NewsFindByIdResponse> update(@PathVariable Long id, @RequestBody NewsRequest newsRequest) {
        News updatedNews = newsService.update(newsMapper.newsRequestToNews(id, newsRequest));

        return ResponseEntity.ok(
                newsMapper.newsToNewsFindByIdResponse(updatedNews)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @NewsOwnerRestriction(RoleType.ROLE_USER)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newsService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
