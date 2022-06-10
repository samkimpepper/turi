package com.turi.turi0411.controller;

import com.turi.turi0411.dto.post.PostDetailDto;
import com.turi.turi0411.dto.post.PostRequestDto;
import com.turi.turi0411.dto.post.PostResponseDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.dto.post.PostSearchDto;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;

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

//    @PostMapping("/create3")
//    public ResponseDto.Default create3(@RequestPart HashMap<String, Object> data, @RequestPart MultipartFile file) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("PostController 생성 이후\n");
//        return postService.create2(file, data, email);
//    }

    @GetMapping("/{postId}")
    public ResponseDto.Data<PostResponseDto.Single> postInfo(@PathVariable(name = "postId") Long postId) {
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
}
