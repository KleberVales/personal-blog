package com.example.blog.dto.comment;

public record CommentResponse(
        Long id,
        Long postId,
        Long authorId,
        String authorUsername,
        String content,
        String createdAt,
        String updatedAt
) {}
