package com.example.blog.service;

import com.example.blog.domain.user.Role;
import com.example.blog.domain.user.User;
import com.example.blog.dto.user.UserUpdateDTO;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    //==============================================================================================
    //                   Load the user when someone tries to log in
    //==============================================================================================

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        User user = repo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado")
                );

        return user; // User IMPLEMENTA UserDetails
    }

    //=====================================================================================
    //                       Creating user in the database
    //=====================================================================================

    public User register(String username, String email, String rawPassword, Role role) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(rawPassword);
        u.setRoles(role);
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





}