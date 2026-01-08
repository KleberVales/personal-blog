package com.example.blog.service;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.user.User;
import com.example.blog.dto.post.PostCreateDTO;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.util.List;

@Service
public class PostService {

    private final PostRepository repo;
    private final UserRepository userRepo;

    public PostService(PostRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    //===================== Insert in the table ==========================================
    public Post create(PostCreateDTO dto, String username) {
        User author = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setAuthor(author);

        return repo.save(post);
    }

    //========================== Read all table =====================================
    public List<Post> findAll() {
        return repo.findAll();
    }

    //======================== Read with id =========================================

    public Post findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    //===================== Update the table ======================================

    public Post update(Long id, PostCreateDTO dto, String username, boolean isAdmin) {
        Post post = findById(id);

        if (!post.getAuthor().getUsername().equals(username) && !isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        post.setTitle(dto.title());
        post.setContent(dto.content());
        return repo.save(post);
    }

    //================ Delete ===============================================

    public void delete(Long id) {
        Post post = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post não encontrado"));

        repo.delete(post);
    }

}

