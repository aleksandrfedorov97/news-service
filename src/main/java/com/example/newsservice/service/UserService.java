package com.example.newsservice.service;

import com.example.newsservice.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User create(User user);
    User update(User user);
    void deleteById(Long id);
}
