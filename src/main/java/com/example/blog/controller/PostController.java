// controller/PostController.java
package com.example.blog.controller;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.PostDtos;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public PostDtos.PostResponse create(@RequestBody @Valid PostDtos.PostCreateRequest req, Principal principal) {
        User author = userService.findByEmailOrThrow(principal.getName());
        Post p = postService.create(req.title, req.content, author);
        return toResponse(p);
    }

    @GetMapping("/{id}")
    public PostDtos.PostResponse get(@PathVariable Long id) {
        return toResponse(postService.get(id));
    }

    @PutMapping("/{id}")
    public PostDtos.PostResponse update(@PathVariable Long id, @RequestBody PostDtos.PostUpdateRequest req, Principal principal) {
        Post p = postService.get(id);
        // optionally check ownership: p.getAuthor().getEmail().equals(principal.getName())
        p = postService.update(p, req.title, req.content);
        return toResponse(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        // optionally check ownership
        postService.delete(id);
    }

    // Search, filtering, sorting, pagination
    @GetMapping
    public Page<PostDtos.PostResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Sort s = Sort.by(sort.split(",")[0]);
        if (sort.toUpperCase().endsWith("DESC")) s = s.descending();
        Pageable pageable = PageRequest.of(page, size, s);
        Instant fromDt = from != null ? Instant.parse(from) : null;
        Instant toDt = to != null ? Instant.parse(to) : null;

        Page<Post> posts = postService.search(q, authorId, fromDt, toDt, pageable);
        return posts.map(this::toResponse);
    }

    private PostDtos.PostResponse toResponse(Post p) {
        PostDtos.PostResponse r = new PostDtos.PostResponse();
        r.id = p.getId();
        r.title = p.getTitle();
        r.content = p.getContent();
        r.authorId = p.getAuthor().getId();
        r.authorUsername = p.getAuthor().getUsername();
        r.createdAt = p.getCreatedAt().toString();
        r.updatedAt = p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : null;
        return r;
    }
}
