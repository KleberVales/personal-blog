// CommentDtos.java
package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentDtos {

    public static class CommentCreateRequest {
        @NotBlank
        public String body;
    }

    public static class CommentResponse {
        public Long id;
        public String body;
        public Long postId;
        public Long authorId;
        public String authorUsername;
        public String createdAt;
    }
}
