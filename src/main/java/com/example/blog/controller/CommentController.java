// controller/CommentController.java
package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.CommentCreateRequest;
import com.example.blog.dto.CommentResponse;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public CommentResponse add(@PathVariable Long postId, @RequestBody @Valid CommentCreateRequest req, Principal principal) {
        Post post = postService.get(postId);
        User author = userService.findByEmailOrThrow(principal.getName());
        Comment c = commentService.create(req.body, post, author);
        return toResponse(c);
    }

    @GetMapping
    public Page<CommentResponse> list(@PathVariable Long postId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Post post = postService.get(postId);
        return commentService.listByPost(post, PageRequest.of(page, size)).map(this::toResponse);
    }

    private CommentResponse toResponse(Comment c) {
        CommentResponse r = new CommentResponse();
        r.id = c.getId();
        r.body = c.getBody();
        r.postId = c.getPost().getId();
        r.authorId = c.getAuthor().getId();
        r.authorUsername = c.getAuthor().getUsername();
        r.createdAt = c.getCreatedAt().toString();
        return r;
    }
}
