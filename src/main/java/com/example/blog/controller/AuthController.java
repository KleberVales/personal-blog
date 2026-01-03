// controller/AuthController.java
package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.AuthResponse;
import com.example.blog.dto.LoginRequest;
import com.example.blog.dto.RegisterRequest;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager; // if not auto-configured, wire differently

    public AuthController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
        User u = userService.register(req.username, req.email, req.password);
        AuthResponse res = new AuthResponse();
        res.userId = u.getId();
        res.username = u.getUsername();
        res.token = "basic"; // placeholder; replace with JWT in real setup
        return res;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password)
        );
        User principal = userService.findByEmailOrThrow(req.email);
        AuthResponse res = new AuthResponse();
        res.userId = principal.getId();
        res.username = principal.getUsername();
        res.token = "basic"; // placeholder
        return res;
    }
}
