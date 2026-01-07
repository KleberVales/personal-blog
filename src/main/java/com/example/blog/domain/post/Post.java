package com.example.blog.domain.post;

import jakarta.persistence.*;
import com.example.blog.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    private LocalDateTime createdAt = LocalDateTime.now();



    // getters e setters

    public void setTitle(String title) {
        this.title = title;

    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
