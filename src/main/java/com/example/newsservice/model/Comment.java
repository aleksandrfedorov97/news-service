package com.example.newsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private Long id;
    private String message;
    private User author;
    private News news;
    private Instant createAt;
}
