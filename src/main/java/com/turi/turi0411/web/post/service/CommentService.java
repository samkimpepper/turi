package com.turi.turi0411.web.post.service;

import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.web.post.domain.Comment;
import com.turi.turi0411.web.post.domain.Post;
import com.turi.turi0411.web.post.dto.CommentRequestDto;
import com.turi.turi0411.web.user.domain.User;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.web.post.repository.CommentRepository;
import com.turi.turi0411.web.post.repository.PostRepository;
import com.turi.turi0411.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ResponseDto responseDto;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    public ResponseDto.Default createComment(CommentRequestDto.Save save, String userEmail) {
        User user = userService.findByEmail(userEmail);

        Post post = postRepository.findById(save.getPostId()).orElseThrow(NotFoundException::new);

        Comment comment = Comment.builder()
                .content(save.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
        return responseDto.success("댓글 달기 성공");
    }

    public ResponseDto.Default deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);

        commentRepository.delete(comment);
        return responseDto.success("댓글 삭제 성공");
    }

    public boolean deleteAllByPost(Post post) {
        commentRepository.deleteAllByPost(post);
        return true;
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
