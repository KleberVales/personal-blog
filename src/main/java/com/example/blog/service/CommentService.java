package com.example.blog.service;

import com.example.blog.domain.comment.Comment;
import com.example.blog.domain.post.Post;
import com.example.blog.domain.user.User;
import com.example.blog.dto.comment.CommentCreateDTO;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository commentRepo,
                          PostRepository postRepo,
                          UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    // CREATE
    public Comment create(CommentCreateDTO dto, String username) {
        User author = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepo.findById(dto.postId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(dto.content());
        comment.setAuthor(author);
        comment.setPost(post);

        return commentRepo.save(comment);
    }

    // READ (por post)
    public List<Comment> findByPost(Long postId) {
        return commentRepo.findByPostId(postId);
    }

    // UPDATE
    public Comment update(Long id, CommentCreateDTO dto, String username, boolean isAdmin) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username) && !isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        comment.setContent(dto.content());
        return commentRepo.save(comment);
    }

    // DELETE
    public void delete(Long id, String username, boolean isAdmin) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username) && !isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        commentRepo.delete(comment);
    }
}
