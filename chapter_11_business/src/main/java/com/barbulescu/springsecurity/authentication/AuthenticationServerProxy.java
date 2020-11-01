package com.barbulescu.springsecurity.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.OK;

@Component
public class AuthenticationServerProxy {

    private final RestTemplate rest;
    private final String baseUrl;

    public AuthenticationServerProxy(
            RestTemplate rest,
            @Value("${auth.server.base.url}") String baseUrl) {
        this.rest = rest;
        this.baseUrl = baseUrl;
    }

    public void sendAuth(String username, String password) {
        String url = baseUrl + "/user/auth";

        var body = User.builder()
                .username(username)
                .password(password)
                .build();

        var request = new HttpEntity<>(body);

        rest.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username, String code) {
        String url = baseUrl + "/otp/check";

        var body = User.builder()
                .username(username)
                .code(code)
                .build();

        var request = new HttpEntity<>(body);

        var response = rest.postForEntity(url, request, Void.class);

        return response.getStatusCode().equals(OK);
    }
}

