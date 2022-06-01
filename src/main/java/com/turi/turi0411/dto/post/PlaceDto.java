package com.turi.turi0411.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDto {
    private String placeName;
    private String roadAddress;
    private String jibunAddress;
    private float x;
    private float y;
}
