package com.turi.turi0411.controller;

import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.dto.post.PlaceDto;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final PlaceService placeService;

    @GetMapping("/exception")
    public ResponseDto.Default exceptionTest() {
        throw new NotFoundException("유저를 찾을수 없음이란 메세지가 떠야하는데");
    }


    @GetMapping("/location")
    public ResponseDto.DataList<PlaceDto> mbrTest() {
        return new ResponseDto.DataList<>(placeService.getNearPlaces(127.1090094, 37.5099490), "가까운 장소들");
    }

}
