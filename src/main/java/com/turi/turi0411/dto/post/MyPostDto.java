package com.turi.turi0411.dto.post;


import com.turi.turi0411.entity.Post;
import lombok.Builder;

@Builder
public class MyPostDto {
    private Long postId;
    private String postImageUrl;

    public static MyPostDto postToDto(Post post) {
        return MyPostDto.builder()
                .postId(post.getId())
                .postImageUrl(post.getPostImageUrl())
                .build();
    }
}
