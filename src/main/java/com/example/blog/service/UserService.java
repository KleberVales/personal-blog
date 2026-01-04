package com.example.blog.service;

import com.example.blog.domain.User;
import com.example.blog.dto.user.UserUpdateDTO;
import com.example.blog.exception.NotFoundException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    //=====================================================================================
    //                       searching for a specific user
    //=====================================================================================

    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    //=====================================================================================
    //                       Update a user
    //=====================================================================================

    @Transactional
    public User updateUser(Long id, UserUpdateDTO dto) {

        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        return user; // JPA faz o UPDATE automaticamente
    }


    //=====================================================================================
    //                       Delete a user
    //=====================================================================================

    public void delete(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        repo.delete(user);
    }

    //=====================================================================================
    //                       User authentication via email
    //=====================================================================================

    public User findByEmailOrThrow(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }



}
