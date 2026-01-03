// PostDtos.java
package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;

public class PostDtos {

    public static class PostCreateRequest {
        @NotBlank public String title;
        @NotBlank public String content;
    }

    public static class PostUpdateRequest {
        public String title;
        public String content;
    }

    public static class PostResponse {
        public Long id;
        public String title;
        public String content;
        public Long authorId;
        public String authorUsername;
        public String createdAt;
        public String updatedAt;
    }
}
