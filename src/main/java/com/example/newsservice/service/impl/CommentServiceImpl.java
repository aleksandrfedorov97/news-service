package com.example.newsservice.service.impl;

import com.example.newsservice.exceptions.EntityNotFoundException;
import com.example.newsservice.model.Comment;
import com.example.newsservice.model.News;
import com.example.newsservice.repository.CommentRepository;
import com.example.newsservice.repository.NewsRepository;
import com.example.newsservice.security.UserDetailsImpl;
import com.example.newsservice.service.CommentService;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllByNewsId(Long newsId) {

        return commentRepository.findAllByNewsId(newsId);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                MessageFormat.format("Comment with ID {0} not found!", id)
                        )
                );
    }

    @Override
    public Comment create(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        comment.setAuthor(userDetails.getUser());

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        comment.setAuthor(userDetails.getUser());

        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);

        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
