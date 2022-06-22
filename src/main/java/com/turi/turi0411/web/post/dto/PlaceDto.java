package com.turi.turi0411.web.post.dto;

import com.turi.turi0411.web.post.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDto {
    private Long placeId;
    private String placeName;
    private String roadAddress;
    private String jibunAddress;
    private double x;
    private double y;
    private String placeType;

    public static PlaceDto placeToDto(Place place) {
        return PlaceDto.builder()
                .placeId(place.getId())
                .placeName(place.getPlaceName())
                .roadAddress(place.getRoadAddress())
                .jibunAddress(place.getJibunAddress())
                .x(place.getX())
                .y(place.getY())
                .placeType(place.getType().name())
                .build();
    }

}
