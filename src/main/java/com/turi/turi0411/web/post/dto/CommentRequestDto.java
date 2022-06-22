package com.turi.turi0411.web.post.dto;

import lombok.Getter;

public class CommentRequestDto {
    @Getter
    public static class Save {
        private Long postId;
        private String content;
    }
}
