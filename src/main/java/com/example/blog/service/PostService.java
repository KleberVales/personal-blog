// service/PostService.java
package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public Post create(String title, String content, User author) {
        Post p = new Post();
        p.setTitle(title);
        p.setContent(content);
        p.setAuthor(author);
        return repo.save(p);
    }

    public Post update(Post p, String title, String content) {
        if (title != null) p.setTitle(title);
        if (content != null) p.setContent(content);
        p.setUpdatedAt(Instant.now());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Post get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    // Search + filter via Specification
    public Page<Post> search(String keyword, Long authorId, Instant from, Instant to, Pageable pageable) {
        Specification<Post> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.isBlank()) {
                String like = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("content")), like)
                ));
            }
            if (authorId != null) {
                predicates.add(cb.equal(root.get("author").get("id"), authorId));
            }
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), to));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return repo.findAll(spec, pageable);
    }
}
