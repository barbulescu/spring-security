package com.barbulescu.springsecurity.authentication;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
    String username;
    String password;
    String code;
}