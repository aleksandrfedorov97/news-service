package com.example.newsservice.web.controllers.v1;

import com.example.newsservice.aop.user.UserOwnerRestriction;
import com.example.newsservice.model.User;
import com.example.newsservice.model.user.RoleType;
import com.example.newsservice.service.UserService;
import com.example.newsservice.web.mapper.UserMapper;
import com.example.newsservice.web.model.dto.user.UserListResponse;
import com.example.newsservice.web.model.dto.user.UserRequest;
import com.example.newsservice.web.model.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
            userMapper.userListToUserListResponse(
                    userService.findAll()
            )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @UserOwnerRestriction(RoleType.ROLE_USER)
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToUserResponse(
                        userService.findById(id)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @UserOwnerRestriction(value = RoleType.ROLE_USER, failureMessage = "Current user has the right to update information only about himself!")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User updatedUser = userService.update(userMapper.userRequestToUser(id, userRequest));

        return ResponseEntity.ok(
                        userMapper.userToUserResponse(updatedUser)
                );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @UserOwnerRestriction(value = RoleType.ROLE_USER, failureMessage = "Current user cannot delete other users' profiles!")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
