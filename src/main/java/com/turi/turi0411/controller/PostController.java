package com.turi.turi0411.controller;

import com.turi.turi0411.dto.PostRequestDto;
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

    @PostMapping("/create")
    public ResponseDto.Default create(@RequestBody PostRequestDto.Save save,
                                      @AuthenticationPrincipal User user) {
        return postService.create(save, user);
    }
}
