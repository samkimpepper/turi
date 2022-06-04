package com.turi.turi0411.dto.post;

import com.turi.turi0411.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearchDto {
    private Long postId;
    private String content;
    private String postType;
    private String postImageUrl;
    private String roadAddress;
    private String placeName;
    private double x;
    private double y;

    public static PostSearchDto postToDto(Post post) {
        return PostSearchDto.builder()
                .postId(post.getId())
                .content(post.getContent())
                .postType(post.getType().name())
                .postImageUrl(post.getPostImage())
                .roadAddress(post.getRoadAddress())
                .placeName(post.getPlaceName())
                .x(post.getX())
                .y(post.getY())
                .build();
    }
}
