package com.turi.turi0411.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PostResponseDto {

    @Setter
    @Getter
    @Builder
    public static class Single {
        private String nickname;
        //private String profileImage; 이것도 전달 해야겠지?..
        private String content;
        private String postType;
    }
}
