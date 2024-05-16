package com.example.newsservice.aop.news;

import com.example.newsservice.aop.AbstractOwnerRestrictionAspect;
import com.example.newsservice.model.News;
import com.example.newsservice.model.User;
import com.example.newsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NewsOwnerRestrictionAspect extends AbstractOwnerRestrictionAspect {

    @Autowired
    private final NewsService newsService;

    @Override
    protected User getAuthor(Long entityId) {
        News news = newsService.findById(entityId);
        return news.getAuthor();
    }

    @Before("@annotation(newsOwnerRestriction)")
    public void checkIsOwner(JoinPoint joinPoint, NewsOwnerRestriction newsOwnerRestriction) {
        checkIsOwner(joinPoint, newsOwnerRestriction.value(), newsOwnerRestriction.failureMessage());
    }
}
