package com.example.newsservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

@UtilityClass
public class AuthorizationUtils {

    private static final String HTTP_USER_HEADER = "X-User-ID";

    @Autowired
    private HttpServletRequest request;

    public Long getCurrentUserId() {
        String headerValue = request.getHeader(HTTP_USER_HEADER);

        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }

        Long userId = Long.parseLong(headerValue);

        return userId;
    }
}
