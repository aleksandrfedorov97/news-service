package com.example.newsservice.aop;

import com.example.newsservice.model.Comment;
import com.example.newsservice.model.News;
import com.example.newsservice.service.CommentService;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.utils.AuthorizationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OwnerRestrictionAspect {
    @Autowired
    private NewsService  newsService;

    @Autowired
    private CommentService commentService;

    @Before("@annotation(OwnerRestriction)")
    public void checkIsOwner (JoinPoint joinPoint) {
        Long userId = AuthorizationUtils.getCurrentUserId();

        if (userId == null) {
            throw new SecurityException("Not authorized!");
        }

        String targetClassName = joinPoint.getTarget().getClass().getSimpleName();

        if (targetClassName.endsWith("Controller")) {
            Object[] args = joinPoint.getArgs();

            Long entityId = Long.parseLong(String.valueOf(args[0]));

            String entityType = targetClassName.substring(0, targetClassName.length() - "Controller".length());

            switch (entityType) {
                case "News":
                    News news = newsService.findById(entityId);
                    if (!news.getAuthor().getId().equals(userId)) {
                        throw new SecurityException("User is not author entity!");
                    }
                    break;
                case "Comment":
                    Comment comment = commentService.findById(entityId);
                    if (!comment.getAuthor().getId().equals(userId)) {
                        throw new SecurityException("User is not author entity!");
                    }
                    break;
            }
        }
    }

}
