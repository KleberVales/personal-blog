package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.user.UserRegisterDTO;
import com.example.blog.dto.user.UserResponseDTO;
import com.example.blog.dto.user.UserUpdateDTO;
import com.example.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //=================================================
    //       searching for a specific user
    //=================================================

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        User user = userService.findById(id);

        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.ok(dto);
    }

    //=================================================
    //       Update a user
    //=================================================

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto) {

        User updated = userService.updateUser(id, dto);

        UserResponseDTO response = new UserResponseDTO(
                updated.getId(),
                updated.getUsername(),
                updated.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    //=================================================
    //       Delete a user
    //=================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

}
