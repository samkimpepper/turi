package com.turi.turi0411.repository;

import com.turi.turi0411.entity.Place;
import com.turi.turi0411.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByRoadAddressContaining(String keyword);

    List<Post> findAllByPlace(Place place);
}
