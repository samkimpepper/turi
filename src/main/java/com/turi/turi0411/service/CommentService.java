package com.turi.turi0411.service;

import com.turi.turi0411.dto.CommentRequestDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.entity.Comment;
import com.turi.turi0411.entity.Post;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.repository.CommentRepository;
import com.turi.turi0411.repository.PostRepository;
import com.turi.turi0411.repository.UserRepository;
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
        if(user == null) {
            return responseDto.fail("존재하지 않는 유저", HttpStatus.NOT_FOUND);
        }

        Optional<Post> postOptional = postRepository.findById(save.getPostId());
        if(!postOptional.isPresent()) {
            return responseDto.fail("존재하지 않는 포스트", HttpStatus.NOT_FOUND);
        }
        Post post = postOptional.get();

        Comment comment = Comment.builder()
                .content(save.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
        return responseDto.success("댓글 달기 성공");
    }

    public ResponseDto.Default deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("존재하지 않는 댓글"));

        commentRepository.delete(comment);
        return responseDto.success("댓글 삭제 성공");
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
