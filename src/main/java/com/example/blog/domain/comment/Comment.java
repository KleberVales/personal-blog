package com.example.blog.domain.comment;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.user.User;
import com.example.blog.dto.user.UserRegisterDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime createdAt = LocalDateTime.now();



    // getters e setters
    public void setContent(String content) {
        this.content = content;

    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setPost(Post post) {

        this.post = post;
    }

    public User getAuthor() {

        return author;
    }
}

