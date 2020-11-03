package com.barbulescu.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.function.Function;

@Configuration
@EnableWebFlux
@EnableReactiveMethodSecurity
public class ProjectConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .anyExchange()
                .access(this::getAuthorizationDecisionMono)
                .and().httpBasic()
                .and().build();
    }


    private Mono<AuthorizationDecision> getAuthorizationDecisionMono(Mono<Authentication> a, AuthorizationContext c) {
        String path = getRequestPath(c);

        boolean restrictedTime = LocalTime.now().isAfter(LocalTime.NOON);

        if (path.equals("/hello3")) {
            return a.map(isAdmin())
                    .map(auth -> auth && !restrictedTime)
                    .map(AuthorizationDecision::new);
        }

        return Mono.just(new AuthorizationDecision(false));
    }


    private String getRequestPath(AuthorizationContext c) {
        return c.getExchange()
                .getRequest()
                .getPath()
                .toString();
    }

    private Function<Authentication, Boolean> isAdmin() {
        return p -> p.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        var u1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")
                .build();

        var  u2 = User.withUsername("bill")
                .password("12345")
                .roles("REGULAR_USER")
                .build();

        return new MapReactiveUserDetailsService(u1, u2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}