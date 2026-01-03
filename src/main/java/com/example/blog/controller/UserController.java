package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.service.UserService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //=================================================
    //       endpoint for data entry
    //=================================================

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.register(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }


}
