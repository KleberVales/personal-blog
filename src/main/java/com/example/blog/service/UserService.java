// service/UserService.java
package com.example.blog.service;

import com.example.blog.domain.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String username, String email, String rawPassword) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(rawPassword));
        return repo.save(u);
    }

    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findByEmailOrThrow(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
