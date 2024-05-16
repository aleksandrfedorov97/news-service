package com.example.newsservice.aop;

import com.example.newsservice.exceptions.NotAuthorizedException;
import com.example.newsservice.exceptions.NotPermittedException;
import com.example.newsservice.model.User;
import com.example.newsservice.model.user.Role;
import com.example.newsservice.model.user.RoleType;
import com.example.newsservice.security.UserDetailsImpl;
import org.aspectj.lang.JoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractOwnerRestrictionAspect {
    protected abstract User getAuthor(Long entityId);

    protected Long getEntityId(Object[] args) {
        return Long.parseLong(String.valueOf(args[0]));
    }

    public void checkIsOwner(JoinPoint joinPoint, RoleType[] roleTypes, String failureMessage)  {
        String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        if (!targetClassName.endsWith("Controller")) {
            throw new UnsupportedOperationException("@OwnerRestriction supported only with Controllers!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new NotAuthorizedException("Not authorized!");
        }

        Long entityId = getEntityId(joinPoint.getArgs());
        User author = getAuthor(entityId);

        List<Role> userRoles = new ArrayList<>(userDetails.getUser().getRoles());

        for (Role role : userDetails.getUser().getRoles()) {
            if (Arrays.asList(roleTypes).contains(role.getAuthority())) {
                userRoles.remove(role);
            }
        }

        if (userRoles.size() == 0) {
            if (!author.getId().equals(userDetails.getUser().getId())) {
                throw new NotPermittedException(failureMessage);
            }
        }
    }
}
