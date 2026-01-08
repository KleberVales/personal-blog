package com.example.blog.repository;

import com.example.blog.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long postId);
    List<Comment> findByAuthor_Id(Long authorId);
    boolean existsByIdAndAuthor_Id(Long commentId, Long authorId);

}

