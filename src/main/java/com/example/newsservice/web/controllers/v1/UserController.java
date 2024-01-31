package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.model.User;
import com.example.newsservice.service.UserService;
import com.example.newsservice.web.mapper.UserMapper;
import com.example.newsservice.web.model.user.UserListResponse;
import com.example.newsservice.web.model.user.UserRequest;
import com.example.newsservice.web.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
            userMapper.userListToUserListResponse(
                    userService.findAll()
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToUserResponse(
                        userService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        User newUser = userService.create(userMapper.userRequestToUser(userRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToUserResponse(newUser)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User updatedUser = userService.update(userMapper.userRequestToUser(id, userRequest));

        return ResponseEntity.ok(
                        userMapper.userToUserResponse(updatedUser)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
