package com.barbulescu.springsecurity.web;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class RequestValidationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestId = request.getHeader("Request-Id");

        if (requestId == null || requestId.isBlank()) {
            response.setStatus(SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
