package com.turi.turi0411.controller;

import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.dto.post.PlaceDto;
import com.turi.turi0411.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/near")
    public ResponseDto.DataList<PlaceDto> getNearPlaces(@RequestParam("x") String x, @RequestParam("y") String y, @RequestParam("type") String type) {
        double doublex = Double.parseDouble(x);
        double doubley = Double.parseDouble(y);
        return new ResponseDto.DataList<>(placeService.getNearPlaces(doublex, doubley, type), "가까운 장소들");
    }
}
