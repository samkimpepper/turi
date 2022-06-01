package com.turi.turi0411.controller;

import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/exception")
    public ResponseDto.Default exceptionTest() {
        throw new NotFoundException("유저를 찾을수 없음이란 메세지가 떠야하는데");
    }

}
