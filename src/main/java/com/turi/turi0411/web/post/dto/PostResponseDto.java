package com.turi.turi0411.web.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PostResponseDto {

    // 아마 디테일뷰
    @Setter
    @Getter
    @Builder
    public static class Single {
        private Long postId;
        private String nickname;
        private String email;
        private String profileImageUrl;
        private String content;
        private String postType;
        private String postImageUrl;
        private int rating;
        private List<PostCommentDto> commentList;
    }
}
