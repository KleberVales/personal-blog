package com.example.blog.dto.user;

import com.example.blog.domain.user.Role;

public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private Role role;

    public UserResponseDTO() {

    }

    public UserResponseDTO(Long id, String username, String email, Role role) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}