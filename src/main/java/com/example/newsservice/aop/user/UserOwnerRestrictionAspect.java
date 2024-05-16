package com.example.newsservice.aop.user;

import com.example.newsservice.aop.AbstractOwnerRestrictionAspect;
import com.example.newsservice.model.News;
import com.example.newsservice.model.User;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserOwnerRestrictionAspect extends AbstractOwnerRestrictionAspect {

    @Override
    protected User getAuthor(Long entityId) {
        User user = User.builder()
                .id(entityId)
                .build();
        return user;
    }

    @Before("@annotation(userOwnerRestriction)")
    public void checkIsOwner(JoinPoint joinPoint, UserOwnerRestriction userOwnerRestriction) {
        checkIsOwner(joinPoint, userOwnerRestriction.value(), userOwnerRestriction.failureMessage());
    }
}
