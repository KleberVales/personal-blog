package com.example.blog.controller;

import com.example.blog.dto.auth.AuthRequestDTO;
import com.example.blog.dto.auth.AuthResponseDTO;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtTokenProvider jwt) {
        this.repo = repo; this.encoder = encoder; this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO req) {
        // implementar autenticação manual simples: buscar user por username/email e comparar password
        var userOpt = repo.findByUsernameOrEmail(req.usernameOrEmail(), req.usernameOrEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        var user = userOpt.get();
        if (!encoder.matches(req.password(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        Set<String> roles = Set.of(user.getRole().name());
        String token = jwt.createToken(user.getUsername(), roles);
        return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer"));
    }


}

