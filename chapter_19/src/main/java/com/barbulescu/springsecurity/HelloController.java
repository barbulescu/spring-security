package com.barbulescu.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("/hello")
    Mono<String> sayHello(Mono<Authentication> auth) {
        return auth
                .map(Principal::getName)
                .map(a -> String.format("Hello %s!", a));
    }

    @GetMapping("/hello2")
    Mono<String> sayHello2() {
        return sayHello(ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication));
    }

    @GetMapping("/ciao")
    public Mono<String> ciao() {
        return Mono.just("Ciao!");
    }
}
