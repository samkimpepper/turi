package com.turi.turi0411.repository;

import com.turi.turi0411.entity.Comment;
import com.turi.turi0411.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    @Transactional
    void deleteAllByPost(Post post);
}
