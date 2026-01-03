package com.example.blog.dto;

public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;

    public UserResponseDTO() {

    }

    public UserResponseDTO(Long id, String username, String email) {
        this.userId = id;
        this.username = username;
        this.email = email;
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
