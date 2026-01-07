package com.example.blog.service;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.user.User;
import com.example.blog.dto.post.PostCreateDTO;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repo;
    private final UserRepository userRepo;

    public PostService(PostRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Post create(PostCreateDTO dto, String username) {
        User author = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setAuthor(author);

        return repo.save(post);
    }

    public List<Post> findAll() {
        return repo.findAll();
    }

    public Post findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}

