package com.turi.turi0411.service;

import com.turi.turi0411.dto.comment.PostCommentDto;
import com.turi.turi0411.dto.post.PlaceDto;
import com.turi.turi0411.dto.post.PostRequestDto;
import com.turi.turi0411.dto.post.PostResponseDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.dto.post.PostSearchDto;
import com.turi.turi0411.entity.*;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.repository.PlaceRepository;
import com.turi.turi0411.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final ResponseDto responseDto;
    private final PostRepository postRepository;
    private final PlaceService placeService;
    private final CommentService commentService;
    private final UserService userService;

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

    public ResponseDto.Default create2(HashMap<String, Object> data, String email) {
        User user = userService.findByEmail(email);

        Place place = placeService.create(PlaceDto.builder()
                .placeName(data.get("placeName").toString())
                .jibunAddress(data.get("jibunAddress").toString())
                .roadAddress(data.get("roadAddress").toString())
                .x(Float.parseFloat(data.get("x").toString()))
                .y(Float.parseFloat(data.get("y").toString()))
                .build());

        Post post = Post.builder()
                .content(data.get("content").toString())
                .user(user)
                .place(place)
                .build();

        postRepository.save(post);

        return responseDto.success("포스트 등록 성공");
    }

    public PostResponseDto.Single postInfo(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(!postOptional.isPresent()) {
            return null;
        }

        Post post = postOptional.get();

        List<Comment> comments = commentService.findAllByPost(post);
        List<PostCommentDto> commentList = comments
                .stream()
                .map(comment -> {
                    return PostCommentDto.commentToDto(comment);
                })
                .collect(Collectors.toList());

        PostResponseDto.Single single = PostResponseDto.Single.builder()
                .nickname(post.getUser().getNickname())
                .profileImageUrl(post.getUser().getProfileImageUrl())
                .content(post.getContent())
                .postType(post.getType().name())
                .postImageUrl(post.getPostImage())
                .commentList(commentList)
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

    public ResponseDto.Default deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("존재하지 않는 포스트"));

        postRepository.delete(post);
        return responseDto.success("포스트 삭제 성공");
    }


}
