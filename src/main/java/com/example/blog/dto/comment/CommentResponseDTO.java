package com.example.blog.dto.comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String content,
        String author,
        Long postId,
        LocalDateTime createdAt
) {}
