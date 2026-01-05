package com.example.blog.dto.auth;

import com.example.blog.domain.user.Role;
import java.util.Set;

public record RegisterUserDTO(String username, String email, String password, Set<Role> roles) {}

