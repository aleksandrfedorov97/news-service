package com.example.newsservice.web.mapper;

import com.example.newsservice.model.NewsCategory;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryListResponse;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryRequest;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsCategoryMapper {

    NewsCategory newsCategoryRequestToNewsCategory(NewsCategoryRequest newsCategoryRequest);

    @Mapping(source = "newsCategoryId", target = "id")
    NewsCategory newsCategoryRequestToNewsCategory(Long newsCategoryId, NewsCategoryRequest newsCategoryRequest);

    NewsCategoryResponse newsCategoryToNewsCategoryResponse(NewsCategory newsCategory);

    default NewsCategoryListResponse newsCategoryListToNewsCategoryListResponse(List<NewsCategory> newsCategories) {
        NewsCategoryListResponse newsCategoryListResponse = new NewsCategoryListResponse();

        newsCategoryListResponse.setNewsCategories(
                newsCategories.stream()
                        .map(this::newsCategoryToNewsCategoryResponse)
                        .collect(Collectors.toList())
        );

        return newsCategoryListResponse;
    }


}
