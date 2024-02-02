package com.example.newsservice.web.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> comments = new ArrayList<>();
}
