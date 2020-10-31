package com.barbulescu.springsecurity.web;

import org.springframework.http.HttpMethod;
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
        http.httpBasic();

        //        http.formLogin()
//                .defaultSuccessUrl("/main", true);

//        http.authorizeRequests()
//                .mvcMatchers("/hello/*").hasRole("ADMIN")
//                .mvcMatchers("/ciao/*").hasRole("MANAGER")
//                .anyRequest()
//                .hasAnyAuthority("READ", "WRITE")
//                .access("hasAuthority('READ') and !hasAuthority('DELETE')")
//                .access("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12, 0))")
//                .hasRole("ADMIN")
//                .authenticated()

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
                .anyRequest()
                .denyAll();

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
