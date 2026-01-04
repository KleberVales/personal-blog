package com.example.blog.service;

import com.example.blog.domain.User;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    //=====================================================================================
    //                       Creating user in the database
    //=====================================================================================

    public User register(String username, String email, String rawPassword) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(rawPassword);
        return repo.save(u);
    }

    //=====================================================================================
    //                       Searching for all users in the database
    //=====================================================================================

    public List<User> findAll() {
        return repo.findAll();
    }


}
