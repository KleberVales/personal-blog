// domain/Post.java
package com.example.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_posts_title", columnList = "title"),
        @Index(name = "idx_posts_created_at", columnList = "createdAt")
})
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User author;

    private Instant createdAt = Instant.now();
    private Instant updatedAt;

    // getters/setters
}
