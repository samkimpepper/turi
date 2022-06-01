package com.turi.turi0411.dto;

import lombok.Getter;
import lombok.Setter;

public class CommentRequestDto {

    @Getter
    public static class Save {
        private String content;
        private Long postId;
    }

    @Getter
    public static class Delete {
        private Long commentId;
    }
}
