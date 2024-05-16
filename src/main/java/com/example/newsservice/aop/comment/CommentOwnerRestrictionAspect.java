package com.example.newsservice.aop.comment;

import com.example.newsservice.aop.AbstractOwnerRestrictionAspect;
import com.example.newsservice.aop.news.NewsOwnerRestriction;
import com.example.newsservice.model.Comment;
import com.example.newsservice.model.User;
import com.example.newsservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentOwnerRestrictionAspect extends AbstractOwnerRestrictionAspect {

    private final CommentService commentService;

    @Override
    protected User getAuthor(Long entityId) {
        Comment comment = commentService.findById(entityId);
        return comment.getAuthor();
    }

    @Before("@annotation(commentOwnerRestriction)")
    public void checkIsOwner(JoinPoint joinPoint, CommentOwnerRestriction commentOwnerRestriction) {
        checkIsOwner(joinPoint, commentOwnerRestriction.value(), commentOwnerRestriction.failureMessage());
    }
}
