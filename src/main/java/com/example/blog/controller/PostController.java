package com.example.blog.controller;

import com.example.blog.domain.post.Post;
import com.example.blog.dto.post.PostCreateDTO;
import com.example.blog.dto.post.PostResponseDTO;
import com.example.blog.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    //==================== Insert the table ====================================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','POSTER')")
    public PostResponseDTO create(
            @RequestBody PostCreateDTO dto,
            Authentication auth
    ) {
        Post post = service.create(dto, auth.getName());
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getCreatedAt()
        );
    }

    //===================== Read the table ===============================

    @GetMapping
    public List<PostResponseDTO> list() {
        return service.findAll().stream()
                .map(p -> new PostResponseDTO(
                        p.getId(),
                        p.getTitle(),
                        p.getContent(),
                        p.getAuthor().getUsername(),
                        p.getCreatedAt()
                ))
                .toList();
    }



    //======================= Update the table =======================================

    @PutMapping("/{id}")
    public PostResponseDTO update(
            @PathVariable Long id,
            @RequestBody PostCreateDTO dto,
            Authentication auth
    ) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Post post = service.update(id, dto, auth.getName(), isAdmin);
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getCreatedAt()
        );
    }

    //====================== Delete Post ============================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }



}

