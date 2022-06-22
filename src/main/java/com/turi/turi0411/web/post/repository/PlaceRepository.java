package com.turi.turi0411.web.post.repository;

import com.turi.turi0411.web.post.domain.Place;
import com.turi.turi0411.web.post.domain.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByRoadAddress(String roadAddress);

    Optional<Place> findByRoadAddress(String roadAddress);

    List<Place> findAllByRoadAddressContainingAndTypeEquals(String roadAddress, PostType type);
}
