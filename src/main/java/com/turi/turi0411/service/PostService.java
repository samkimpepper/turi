package com.turi.turi0411.service;

import com.turi.turi0411.dto.post.PostRequestDto;
import com.turi.turi0411.dto.post.PostResponseDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.dto.post.PostSearchDto;
import com.turi.turi0411.entity.Post;
import com.turi.turi0411.entity.PostType;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final ResponseDto responseDto;
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto.Default create(PostRequestDto.Save save, User user) {
        String postType = save.getPostType();
        if(!postType.equals("food") && !postType.equals("enjoy") && !postType.equals("stay")) {
            return responseDto.fail("postType 값이 잘못됐음: " + postType, HttpStatus.BAD_REQUEST);
        }

        Post post = Post.builder()
                .user(user)
                .content(save.getContent())
                .type(PostType.valueOf(postType))
                .placeName(save.getPlaceName())
                .roadAddress(save.getRoadAddress())
                .jibunAddress(save.getJibunAddress())
                .x(save.getX())
                .y(save.getY())
                .build();

        postRepository.save(post);

        return responseDto.success("post 등록 성공");
    }

    public PostResponseDto.Single postInfo(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(!postOptional.isPresent()) {
            return null;
        }

        Post post = postOptional.get();
        PostResponseDto.Single single = PostResponseDto.Single.builder()
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .postType(post.getType().name())
                .build();

        return single;
    }

    public List<PostSearchDto> getPostSearchResult(String keyword) {
        List<Post> results = postRepository.findAllByRoadAddressContaining(keyword);

        return results
                .stream()
                .map(post -> {
                    return PostSearchDto.postToDto(post);
                })
                .collect(Collectors.toList());
    }
}
