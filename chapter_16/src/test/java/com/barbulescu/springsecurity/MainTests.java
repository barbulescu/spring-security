package com.barbulescu.springsecurity;

import com.barbulescu.springsecurity.hello.NameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class MainTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private NameService nameService;

    @Test
    @DisplayName("When the method is called without an authenticated user, it throws AuthenticationException")
    void testNameServiceWithNoUser() {
        assertThatExceptionOfType(AuthenticationException.class)
                .isThrownBy(() -> nameService.getName());
    }

    @Test
    @WithMockUser(authorities = "read")
    @DisplayName("When the method is called with an authenticated user having a wrong authority, it throws AccessDeniedException")
    void testNameServiceWithUserButWrongAuthority() {
        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> nameService.getName());
    }

    @Test
    @WithMockUser(authorities = "write")
    @DisplayName("When the method is called with an authenticated user having a correct authority, it returns the expected result")
    void testNameServiceWithUserButCorrectAuthority() {
        var result = nameService.getName();

        assertThat(result).isEqualTo("Fantastico");
    }
}