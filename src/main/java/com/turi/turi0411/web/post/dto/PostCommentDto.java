package com.turi.turi0411.web.post.dto;

import com.turi.turi0411.web.post.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCommentDto {
    private String profileImageUrl;
    private String nickname;
    private String content;

    public static PostCommentDto commentToDto(Comment comment) {
        return PostCommentDto.builder()
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .build();
    }
}
