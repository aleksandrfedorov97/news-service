package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.model.User;
import com.example.newsservice.model.user.Role;
import com.example.newsservice.model.user.RoleType;
import com.example.newsservice.service.UserService;
import com.example.newsservice.web.mapper.UserMapper;
import com.example.newsservice.web.model.dto.user.UserCreateRequest;
import com.example.newsservice.web.model.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/account")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest userCreateRequest, @RequestParam RoleType roleType) {
        User newUser = userMapper.userCreateRequestToUser(userCreateRequest);
        Role role = Role.from(roleType);
        role.setUser(newUser);
        newUser.setRoles(Collections.singletonList(role));

        newUser = userService.create(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToUserResponse(newUser)
                );
    }
}
