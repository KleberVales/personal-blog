package com.example.blog.dto.post;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt
) {}

