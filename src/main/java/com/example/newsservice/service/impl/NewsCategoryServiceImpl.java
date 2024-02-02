package com.example.newsservice.service.impl;

import com.example.newsservice.exceptions.EntityNotFoundException;
import com.example.newsservice.model.NewsCategory;
import com.example.newsservice.repository.NewsCategoryRepository;
import com.example.newsservice.service.NewsCategoryService;
import com.example.newsservice.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCategoryServiceImpl implements NewsCategoryService {
    private final NewsCategoryRepository newsCategoryRepository;


    @Override
    public List<NewsCategory> findAll() {
        return newsCategoryRepository.findAll();
    }

    @Override
    public NewsCategory findById(Long id) {
        return newsCategoryRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                MessageFormat.format("News Category with ID {0} not found!", id)
                        )
                );
    }

    @Override
    public NewsCategory create(NewsCategory newsCategory) {
        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public NewsCategory update(NewsCategory newsCategory) {
        NewsCategory existedNewCategory = findById(newsCategory.getId());

        BeanUtils.copyNonNullProperties(newsCategory, existedNewCategory);

        return newsCategoryRepository.save(existedNewCategory);
    }

    @Override
    public void deleteById(Long id) {
        newsCategoryRepository.deleteById(id);
    }
}
