package com.turi.turi0411.dto.post;

import com.turi.turi0411.dto.comment.PostCommentDto;
import com.turi.turi0411.entity.Comment;
import com.turi.turi0411.entity.Post;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PostDetailDto {
    private Long postId;
    private String nickname;
    private String email;
    private String profileImageUrl;
    private String content;
    private String postType;
    private String postImageUrl;
    private int rating;
    private List<PostCommentDto> commentList;

    public static PostDetailDto PostToDto(Post post, List<Comment> comments) {
        List<PostCommentDto> commentList = comments
                .stream()
                .map(comment -> {
                    return PostCommentDto.commentToDto(comment);
                })
                .collect(Collectors.toList());

        return PostDetailDto.builder()
                .postId(post.getId())
                .nickname(post.getUser().getNickname())
                .email(post.getUser().getEmail())
                .profileImageUrl(post.getUser().getProfileImageUrl())
                .content(post.getContent())
                .postType(post.getPlace().getType().toString())
                .postImageUrl(post.getPostImageUrl())
                .rating(post.getRating())
                .commentList(commentList)
                .build();
    }
}
