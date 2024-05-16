package com.example.newsservice.aop.news;

import com.example.newsservice.model.user.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsOwnerRestriction {
    RoleType[] value() default RoleType.ROLE_USER;
    String failureMessage() default "User is not author entity!";
}
