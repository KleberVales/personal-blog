package com.example.blog.dto.comment;

public record CommentCreateDTO(
        String content,
        Long postId
) {}

