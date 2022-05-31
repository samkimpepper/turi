package com.turi.turi0411.dto.post;

import com.turi.turi0411.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearchDto {
    private String content;
    private String postType;
    private String roadAddress;
    private String placeName;
    private float x;
    private float y;

    public static PostSearchDto postToDto(Post post) {
        return PostSearchDto.builder()
                .content(post.getContent())
                .postType(post.getType().name())
                .roadAddress(post.getRoadAddress())
                .placeName(post.getPlaceName())
                .x(post.getX())
                .y(post.getY())
                .build();
    }
}
