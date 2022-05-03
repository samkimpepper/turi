package com.turi.turi0411.service;

import com.turi.turi0411.dto.PostRequestDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.entity.Post;
import com.turi.turi0411.entity.PostType;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final ResponseDto responseDto;
    private final PostRepository postRepository;

    public ResponseDto.Default create(PostRequestDto.Save save, User user) {
        String postType = save.getType();
        if(!postType.equals("lodging") && !postType.equals("restaurant") && !postType.equals("entertainment")) {
            return responseDto.fail("postType 값이 잘못됐음: " + postType, HttpStatus.BAD_REQUEST);
        }

        Post post = Post.builder()
                .user(user)
                .content(save.getContent())
                .type(PostType.valueOf(postType))
                .jibunAddress(save.getJibunAddress())
                .roadAddress(save.getRoadAddress())
                .placeName(save.getPlaceName())
                .x(save.getX())
                .y(save.getY())
                .build();

        postRepository.save(post);

        return responseDto.success("post 등록 성공");
    }
}
