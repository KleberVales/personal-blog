package com.example.blog.controller;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.post.PostRegisterDTO;
import com.example.blog.dto.post.PostResponseDTO;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    //========================================================================
    //            create post
    //========================================================================

    @PostMapping
    public PostResponseDTO create(@RequestBody @Valid PostRegisterDTO req, Principal principal) {

        User user = userService.findByEmailOrThrow(principal.getName());
        Post p = postService.createPost(req.getTitle(), req.getText(), user);

        return toResponse(p);
    }

    //=========================================================================
    // Maps the Post entity to PostResponseDTO,
    // exposing only the data necessary for the API response
    //=========================================================================

    private PostResponseDTO toResponse(Post p) {

        PostResponseDTO r = new PostResponseDTO();

        r.setId(p.getId());
        r.setUser(p.getUser().getUsername());
        r.setTitle(p.getTitle());
        r.setText(p.getText());

        return r;
    }



}
