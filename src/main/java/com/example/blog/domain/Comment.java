// domain/Comment.java
package com.example.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "comments", indexes = {
        @Index(name = "idx_comments_created_at", columnList = "createdAt")
})
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String body;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User author;

    private Instant createdAt = Instant.now();

    // getters/setters
}
