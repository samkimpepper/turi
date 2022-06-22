package com.turi.turi0411.web.post.repository;

import com.turi.turi0411.web.post.domain.Place;
import com.turi.turi0411.web.post.domain.Post;
import com.turi.turi0411.web.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByRoadAddressContaining(String keyword);

    List<Post> findAllByPlace(Place place);

    List<Post> findAllByUser(User user);
}
