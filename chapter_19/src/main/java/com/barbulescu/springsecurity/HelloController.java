package com.barbulescu.springsecurity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("/hello1")
    Mono<String> sayHello1(Mono<Authentication> auth) {
        return auth
                .map(Principal::getName)
                .map(a -> String.format("Hello %s!", a));
    }

    @GetMapping("/hello2")
    Mono<String> sayHello2() {
        return sayHello1(ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication));
    }

    @GetMapping("/hello3")
    @PreAuthorize("hasRole('ADMIN')")
    Mono<String> sayHello3() {
        return Mono.just("Hello");
    }

    @GetMapping("/ciao")
    public Mono<String> ciao() {
        return Mono.just("Ciao!");
    }
}
