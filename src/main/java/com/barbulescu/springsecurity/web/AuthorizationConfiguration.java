package com.barbulescu.springsecurity.web;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationProviderService authenticationProvider;
    private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;

    public AuthorizationConfiguration(
            AuthenticationProviderService authenticationProvider,
            StaticKeyAuthenticationFilter staticKeyAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.staticKeyAuthenticationFilter = staticKeyAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http
                .addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
