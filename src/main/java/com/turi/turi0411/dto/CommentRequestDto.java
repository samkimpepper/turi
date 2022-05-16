package com.turi.turi0411.dto;

import lombok.Getter;
import lombok.Setter;

public class CommentRequestDto {

    @Getter
    @Setter
    public static class Save {
        private String content;
        private Long postId;
    }
}
