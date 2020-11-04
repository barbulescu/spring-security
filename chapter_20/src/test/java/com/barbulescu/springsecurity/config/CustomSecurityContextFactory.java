package com.barbulescu.springsecurity.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomUser user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var token = new UsernamePasswordAuthenticationToken(user.username(), null, null);
        context.setAuthentication(token);

        return context;
    }
}
