package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.model.NewsCategory;
import com.example.newsservice.service.NewsCategoryService;
import com.example.newsservice.web.mapper.NewsCategoryMapper;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryListResponse;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryRequest;
import com.example.newsservice.web.model.dto.newscategory.NewsCategoryResponse;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/news-category")
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;
    private final NewsCategoryMapper newsCategoryMapper;

    @GetMapping
    public ResponseEntity<NewsCategoryListResponse> findAll() {

        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryListToNewsCategoryListResponse(
                        newsCategoryService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryToNewsCategoryResponse(
                        newsCategoryService.findById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> create(@RequestBody NewsCategoryRequest newsCategoryRequest) {
        NewsCategory newNewsCategory = newsCategoryService.create(
                newsCategoryMapper.newsCategoryRequestToNewsCategory(newsCategoryRequest)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsCategoryMapper.newsCategoryToNewsCategoryResponse(newNewsCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> update(@PathVariable Long id,
                                                       @RequestBody NewsCategoryRequest newsCategoryRequest) {
        NewsCategory updatedNewsCategory = newsCategoryService.update(
                newsCategoryMapper.newsCategoryRequestToNewsCategory(id, newsCategoryRequest)
        );

        return ResponseEntity.ok(
            newsCategoryMapper.newsCategoryToNewsCategoryResponse(updatedNewsCategory)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsCategoryService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
