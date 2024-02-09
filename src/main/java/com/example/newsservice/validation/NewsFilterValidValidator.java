package com.example.newsservice.validation;

import com.example.newsservice.web.model.dto.news.NewsFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewsFilterValidValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {
    @Override
    public boolean isValid(NewsFilter filter, ConstraintValidatorContext constraintValidatorContext) {
        if (filter.getPageNumber() == null || filter.getPageSize() == null) {
            return false;
        }

        return true;
    }
}
