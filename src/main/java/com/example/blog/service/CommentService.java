// service/CommentService.java
package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repo;

    public CommentService(CommentRepository repo) {
        this.repo = repo;
    }

    public Comment create(String body, Post post, User author) {
        Comment c = new Comment();
        c.setBody(body);
        c.setPost(post);
        c.setAuthor(author);
        return repo.save(c);
    }

    public Page<Comment> listByPost(Post post, Pageable pageable) {
        return repo.findByPost(post, pageable);
    }
}
