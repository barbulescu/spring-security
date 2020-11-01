package com.barbulescu.springsecurity.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
