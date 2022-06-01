package com.turi.turi0411.service;

import com.turi.turi0411.dto.post.PlaceDto;
import com.turi.turi0411.entity.Place;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public Place create(PlaceDto placeDto) {
        if(placeRepository.existsByRoadAddress(placeDto.getRoadAddress())) {
            Place place = placeRepository.findByRoadAddress(placeDto.getRoadAddress()).orElseThrow(() -> new NotFoundException("존재하지 않는 장소"));
            return place;
        }

        Place place = Place.builder()
                .placeName(placeDto.getPlaceName())
                .jibunAddress(placeDto.getJibunAddress())
                .roadAddress(placeDto.getRoadAddress())
                .x(placeDto.getX())
                .y(placeDto.getY())
                .build();

        placeRepository.save(place);
        return place;
    }
}
