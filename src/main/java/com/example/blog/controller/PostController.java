package com.example.blog.controller;

import com.example.blog.domain.post.Post;
import com.example.blog.dto.post.PostCreateDTO;
import com.example.blog.dto.post.PostResponseDTO;
import com.example.blog.service.PostService;
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

    @PostMapping
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
}

