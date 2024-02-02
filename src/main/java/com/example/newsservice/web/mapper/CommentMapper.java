package com.example.newsservice.web.mapper;

import com.example.newsservice.model.Comment;
import com.example.newsservice.web.model.dto.comment.CommentListResponse;
import com.example.newsservice.web.model.dto.comment.CommentCreateRequest;
import com.example.newsservice.web.model.dto.comment.CommentResponse;
import com.example.newsservice.web.model.dto.comment.CommentUpdateRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment commentCreateRequestToComment(CommentCreateRequest commentCreateRequest);

    @Mapping(source = "commentId", target = "id")
    Comment commentUpdateRequestToComment(Long commentId, CommentUpdateRequest commentUpdateRequest);

    CommentResponse commentToCommentResponse(Comment comment);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse commentListResponse = new CommentListResponse();

        commentListResponse.setComments(
                comments.stream()
                        .map(this::commentToCommentResponse)
                        .collect(Collectors.toList())
        );

        return commentListResponse;
    }
}
