package com.turi.turi0411.dto.post;

import com.turi.turi0411.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDto {
    private String placeName;
    private String roadAddress;
    private String jibunAddress;
    private double x;
    private double y;

    public static PlaceDto placeToDto(Place place) {
        return PlaceDto.builder()
                .placeName(place.getPlaceName())
                .roadAddress(place.getRoadAddress())
                .jibunAddress(place.getJibunAddress())
                .x(place.getX())
                .y(place.getY())
                .build();
    }
}
