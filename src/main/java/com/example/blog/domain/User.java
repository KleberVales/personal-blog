// domain/User.java
package com.example.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String passwordHash;

    private String role = "USER";

    private Instant createdAt = Instant.now();

    // getters/setters
}
