package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.user.UserRegisterDTO;
import com.example.blog.dto.user.UserResponseDTO;
import com.example.blog.service.UserService;
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

    @PostMapping("/register")
    public UserResponseDTO save(@RequestBody UserRegisterDTO userRegisterDTO) {

        User user = userService.register(userRegisterDTO.getUsername(), userRegisterDTO.getEmail(),
                userRegisterDTO.getPassword());

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUserId(user.getId());
        userResponseDTO.setUsername(user.getUsername());


        return userResponseDTO;

    }

}
