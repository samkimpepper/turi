package com.turi.turi0411.web.post.controller;

import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.web.post.service.PostService;
import com.turi.turi0411.web.post.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create2")
    public ResponseDto.Default create2(MultipartHttpServletRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("PostController create2\n");
        MultipartFile file = request.getFile("file");
        String placeName = request.getParameter("placeName");
        String jibunAddress = request.getParameter("jibunAddress");
        String roadAddress =request.getParameter("roadAddress");
        String postType = request.getParameter("postType");
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        double x = Double.parseDouble(request.getParameter("x"));
        double y = Double.parseDouble(request.getParameter("y"));

        return postService.create2(file, placeName, jibunAddress, roadAddress, postType, content, rating, x, y, email);
    }

    @GetMapping("/{postId}")
    public ResponseDto.Data<PostDetailDto> postInfo(@PathVariable(name = "postId") Long postId) {
        return new ResponseDto.Data<>(postService.postInfo(postId), "성공");
    }

    @GetMapping("/search/{keyword}")
    public ResponseDto.DataList<PostSearchDto> postSearchResult(@PathVariable(name="keyword") String keyword) {
        return new ResponseDto.DataList<>(postService.getPostSearchResult(keyword), "검색 결과긴 한데 도로명주소 기준으로만 함");
    }

    @DeleteMapping("/{postId}")
    public ResponseDto.Default delete(@PathVariable(name ="postId") Long postId) {
        return postService.deletePost(postId);
    }

    @PutMapping
    public ResponseDto.Data<PostDetailDto> modify(@RequestBody PostRequestDto.Modify modify) {
        return new ResponseDto.Data<>(postService.modifyPost(modify), "수정 성공");
    }

    @PutMapping("/like/{postId}")
    public ResponseDto.Default postLike(@PathVariable(name="postId") Long postId) {
        return postService.postLike(postId);
    }

    @GetMapping("/place/{placeId}")
    public ResponseDto.DataList<PostSearchDto> getSamePlacePost(@PathVariable(name="placeId") Long placeId) {
        return new ResponseDto.DataList<>(postService.getSamePlacePost(placeId), "same place");
    }

    @GetMapping("/my")
    public ResponseDto.DataList<MyPostDto> getMyPostList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseDto.DataList<>(postService.getMyPostList(email), "내가 쓴 포스트 목록");
    }
}
