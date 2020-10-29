package com.barbulescu.springsecurity.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Log4j2
public class HelloController {

    @GetMapping("/hello/{name}")
    String sayHello(@PathVariable String name, Authentication authentication) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        log.info("authentication from SecurityContext: {}", securityContext.getAuthentication());
        log.info("injected authentication: {}", authentication);
        return "Hello " + name + "!";
    }

    @GetMapping("/hello/async/{name}")
    @Async
    CompletableFuture<String> sayHelloAsync(@PathVariable String name, Authentication authentication) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        log.info("[async] authentication from SecurityContext: {}", securityContext.getAuthentication());
        log.info("[async] injected authentication: {}", authentication);
        return CompletableFuture.completedFuture("Hello " + name + "!");
    }
}
