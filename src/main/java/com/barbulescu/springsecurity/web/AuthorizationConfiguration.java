package com.barbulescu.springsecurity.web;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationProviderService authenticationProvider;

    public AuthorizationConfiguration(AuthenticationProviderService authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(conf -> {
            conf.realmName("bom");
            conf.authenticationEntryPoint(new CustomEntryPoint());
        });
        http.formLogin()
                .defaultSuccessUrl("/main", true);

        http.authorizeRequests()
                .anyRequest()
//                .hasAnyAuthority("READ", "WRITE")
//                .access("hasAuthority('READ') and !hasAuthority('DELETE')")
//                .access("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12, 0))")
                .hasRole("ADMIN")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
