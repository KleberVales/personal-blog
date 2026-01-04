package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.user.UserRegisterDTO;
import com.example.blog.dto.user.UserResponseDTO;
import com.example.blog.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //=================================================
    //       endpoint for data out
    //=================================================

    @GetMapping
    public List<UserResponseDTO> list() {
        return userService.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();
    }

}
