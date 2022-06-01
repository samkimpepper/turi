package com.turi.turi0411.dto.comment;

import lombok.Getter;

public class CommentRequestDto {
    @Getter
    public static class Save {
        private Long postId;
        private String content;
    }
}
