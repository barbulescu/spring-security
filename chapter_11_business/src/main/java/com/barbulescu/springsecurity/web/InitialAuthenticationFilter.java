package com.barbulescu.springsecurity.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;
    private final String signingKey;

    public InitialAuthenticationFilter(AuthenticationManager manager, String signingKey) {
        this.manager = manager;
        this.signingKey = signingKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(a);
        } else {
            OtpAuthentication otp = new OtpAuthentication(username, code);
            Authentication a = manager.authenticate(otp);

            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(UTF_8));

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}