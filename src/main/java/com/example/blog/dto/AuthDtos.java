// AuthDtos.java
package com.example.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

    public static class RegisterRequest {
        @NotBlank public String username;
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class LoginRequest {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class AuthResponse {
        public String token;
        public Long userId;
        public String username;
    }
}
