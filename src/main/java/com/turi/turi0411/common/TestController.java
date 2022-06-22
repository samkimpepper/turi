package com.turi.turi0411.common;

import com.turi.turi0411.web.post.dto.PlaceDto;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.web.post.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final PlaceService placeService;

    @GetMapping("/location")
    public ResponseDto.DataList<PlaceDto> mbrTest() {
        return new ResponseDto.DataList<>(placeService.getNearPlaces(127.1090094, 37.5099490, "food"), "가까운 장소들");
    }

}
