package com.example.newsservice.service.impl;

import com.example.newsservice.exceptions.EntityNotFoundException;
import com.example.newsservice.model.User;
import com.example.newsservice.repository.UserRepository;
import com.example.newsservice.service.UserService;
import com.example.newsservice.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                MessageFormat.format("User with ID {0} not found!", id)
                        )
                );
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());

        BeanUtils.copyNonNullProperties(user, existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
