package com.barbulescu.springsecurity.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;

import static java.util.Optional.ofNullable;

@Log4j2
public class CsrfTokenLogger implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = ofNullable(request.getAttribute("_csrf"))
                .filter(CsrfToken.class::isInstance)
                .map(CsrfToken.class::cast)
                .map(CsrfToken::getToken)
                .orElse("<>");

        log.info("CSRF token {}", token);

        chain.doFilter(request, response);
    }

}
