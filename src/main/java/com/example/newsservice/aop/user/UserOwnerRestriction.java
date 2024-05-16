package com.example.newsservice.aop.user;

import com.example.newsservice.model.user.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserOwnerRestriction {
    RoleType[] value() default RoleType.ROLE_USER;
    String failureMessage() default "Current user can get information only about himself!";
}
