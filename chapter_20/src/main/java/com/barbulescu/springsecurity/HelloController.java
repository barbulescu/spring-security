package com.barbulescu.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello1")
    String hello1() {
        return "Hello!";
    }

    @GetMapping("/hello2")
    String hello2(Authentication auth) {
        return String.format("Hello %s!", auth.getName());
    }
}
