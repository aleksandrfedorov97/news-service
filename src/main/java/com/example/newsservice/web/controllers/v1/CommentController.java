package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.aop.comment.CommentOwnerRestriction;
import com.example.newsservice.model.Comment;
import com.example.newsservice.model.user.RoleType;
import com.example.newsservice.service.CommentService;
import com.example.newsservice.web.mapper.CommentMapper;
import com.example.newsservice.web.model.dto.comment.CommentListResponse;
import com.example.newsservice.web.model.dto.comment.CommentCreateRequest;
import com.example.newsservice.web.model.dto.comment.CommentResponse;
import com.example.newsservice.web.model.dto.comment.CommentUpdateRequest;
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
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{newsId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentListResponse> findAllByNewsId(@PathVariable Long newsId) {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        commentService.findAllByNewsId(newsId)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> create(@RequestBody CommentCreateRequest commentRequest) {
        Comment newComment = commentService.create(commentMapper.commentCreateRequestToComment(commentRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        commentMapper.commentToCommentResponse(newComment)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @CommentOwnerRestriction({RoleType.ROLE_ADMIN, RoleType.ROLE_USER, RoleType.ROLE_MODERATOR})
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @RequestBody CommentUpdateRequest commentUpdateRequest) {
        Comment updatedComment = commentService.update(
                commentMapper.commentUpdateRequestToComment(id, commentUpdateRequest)
        );

        return ResponseEntity.ok(
                commentMapper.commentToCommentResponse(updatedComment)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @CommentOwnerRestriction(RoleType.ROLE_USER)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
