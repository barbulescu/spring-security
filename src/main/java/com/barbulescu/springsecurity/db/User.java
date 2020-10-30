package com.barbulescu.springsecurity.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;
}
