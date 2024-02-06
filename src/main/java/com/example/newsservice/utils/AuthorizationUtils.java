package com.example.newsservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class AuthorizationUtils {

    private static final String HTTP_USER_HEADER = "X-User-ID";

    public Long getCurrentUserId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String headerValue = request.getHeader(HTTP_USER_HEADER);

        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }

        Long userId = Long.parseLong(headerValue);

        return userId;
    }
}
