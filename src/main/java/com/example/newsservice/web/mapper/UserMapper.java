package com.example.newsservice.web.mapper;

import com.example.newsservice.model.User;
import com.example.newsservice.web.model.user.UserListResponse;
import com.example.newsservice.web.model.user.UserRequest;
import com.example.newsservice.web.model.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userRequestToUser(UserRequest userRequest);

    @Mapping(source = "userId", target = "id")
    User userRequestToUser(Long userId, UserRequest userRequest);
    UserResponse userToUserResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse userListResponse = new UserListResponse();

        userListResponse.setUsers(
                users.stream()
                        .map(this::userToUserResponse)
                        .collect(Collectors.toList())
        );

        return userListResponse;
    }
}
