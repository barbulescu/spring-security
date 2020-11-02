package com.barbulescu.springsecurity.book;

import lombok.Value;

import java.util.List;

@Value
public class Employee {
    String name;
    List<String> books;
    List<String> roles;
}
