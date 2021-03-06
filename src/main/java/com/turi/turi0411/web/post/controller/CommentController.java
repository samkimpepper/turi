package com.turi.turi0411.web.post.controller;


import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.web.post.dto.CommentRequestDto;
import com.turi.turi0411.web.user.domain.User;
import com.turi.turi0411.web.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseDto.Default create(@RequestBody CommentRequestDto.Save save, @AuthenticationPrincipal User user) {
        return commentService.createComment(save, user.getEmail());
    }

    @DeleteMapping("/{commentId}")
    public ResponseDto.Default delete(@PathVariable(name="commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
