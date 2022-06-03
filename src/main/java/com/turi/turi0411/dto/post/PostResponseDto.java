package com.turi.turi0411.dto.post;

import com.turi.turi0411.dto.comment.PostCommentDto;
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
        private String nickname;
        private String profileImageUrl;
        private String content;
        private String postType;
        private String postImageUrl;
        private List<PostCommentDto> commentList;
    }
}
