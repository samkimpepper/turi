package com.turi.turi0411.controller;

import com.turi.turi0411.dto.PostRequestDto;
import com.turi.turi0411.dto.PostResponseDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseDto.Default create(@RequestBody PostRequestDto.Save save,
                                      @AuthenticationPrincipal User user) {
        return postService.create(save, user);
    }

    @GetMapping("/{postId}")
    public ResponseDto.Data<PostResponseDto.Single> postInfo(@PathVariable(name = "postId") Long postId) {
        return new ResponseDto.Data<>(postService.postInfo(postId), "성공");
    }
}
