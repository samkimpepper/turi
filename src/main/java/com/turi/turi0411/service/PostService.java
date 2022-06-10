package com.turi.turi0411.service;

import com.turi.turi0411.config.s3.S3Uploader;
import com.turi.turi0411.dto.comment.PostCommentDto;
import com.turi.turi0411.dto.post.*;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.entity.*;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.repository.PlaceRepository;
import com.turi.turi0411.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final ResponseDto responseDto;
    private final PostRepository postRepository;
    private final PlaceService placeService;
    private final CommentService commentService;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto.Default create(PostRequestDto.Save save, User user) throws ParseException {
        String postType = save.getPostType();
        if(!postType.equals("food") && !postType.equals("enjoy") && !postType.equals("stay")) {
            return responseDto.fail("postType 값이 잘못됐음: " + postType, HttpStatus.BAD_REQUEST);
        }

        Place place = placeService.create(Place.builder()
                .placeName(save.getPlaceName())
                .roadAddress(save.getRoadAddress())
                .jibunAddress(save.getJibunAddress())
                .x(save.getX())
                .y(save.getY())
                .type(PostType.valueOf(postType))
                .build());

        Post post = Post.builder()
                .user(user)
                .content(save.getContent())
                .type(PostType.valueOf(postType))
                .place(place)
                .placeName(save.getPlaceName())
                .roadAddress(save.getRoadAddress())
                .jibunAddress(save.getJibunAddress())
                .x(save.getX())
                .y(save.getY())
                .likeCount(0)
                .build();

        postRepository.save(post);

        return responseDto.success("post 등록 성공");
    }

    public ResponseDto.Default create2(MultipartFile file,
                                       String placeName,
                                       String jibunAddress,
                                       String roadAddress,
                                       String postType,
                                       String content,
                                       int rating,
                                       double x,
                                       double y,
                                       String email) {
        User user = userService.findByEmail(email);
        System.out.println("PostService 내부\n");

        String postImageUrl = null;
        if(!file.isEmpty()) {
            postImageUrl = s3Uploader.uploadFile(file);
            System.out.println("PostService 내부 s3uploader 이후\n");
        }

        Place place = placeService.create(PlaceDto.builder()
                .placeName(placeName)
                .jibunAddress(jibunAddress)
                .roadAddress(roadAddress)
                .x(x)
                .y(y)
                .placeType(postType)
                .build());
        System.out.println("PostService 내부 place 생성 이후\n");

        Post post = Post.builder()
                .content(content)
                .user(user)
                .place(place)
                .postImageUrl(postImageUrl)
                .type(PostType.valueOf(postType))
                .rating(rating)
                .likeCount(0)
                .build();
        System.out.println("PostService 내부 post 빌더 생성 이후\n");

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
                .postId(post.getId())
                .nickname(post.getUser().getNickname())
                .email(post.getUser().getEmail())
                .profileImageUrl(post.getUser().getProfileImageUrl())
                .content(post.getContent())
                .postType(post.getType().name())
                .postImageUrl(post.getPostImageUrl())
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

    @Transactional
    public PostDetailDto modifyPost(PostRequestDto.Modify modify) {
        Post post = postRepository.findById(modify.getPostId()).orElseThrow(() -> new NotFoundException("존재하지 않는 포스트"));

        if(StringUtils.hasText(modify.getContent())) {
            post.setContent(modify.getContent());
        }

        //rating도 해야되는데...

        if(StringUtils.hasText(modify.getPlaceName())) {
            Place place = placeService.create(PlaceDto.builder()
                    .placeName(modify.getPlaceName())
                    .placeType(modify.getPostType())
                    .roadAddress(modify.getRoadAddress())
                    .jibunAddress(modify.getJibunAddress())
                    .x(modify.getX())
                    .y(modify.getY())
                    .build());

            post.setPlace(place);
            log.info("modify post success.");
        }

        List<Comment> comments = commentService.findAllByPost(post);

        return PostDetailDto.PostToDto(post, comments);
    }

    @Transactional
    public ResponseDto.Default postLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("존재하지 않는 포스트"));

        int likeCount = post.increaseLikeCount();
        return responseDto.success("좋아요 처리 성공");
    }

    public List<PostSearchDto> getSamePlacePost(Long placeId) {
        Place place = placeService.findById(placeId);

        List<Post> results = postRepository.findAllByPlace(place);

        List<PostSearchDto> postSearchDtos = results.stream()
                .map(post -> {
                    return PostSearchDto.postToDto(post);
                })
                .collect(Collectors.toList());
        return postSearchDtos;
    }

}
