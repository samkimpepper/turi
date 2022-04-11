package com.turi.turi0411.controller;

import com.turi.turi0411.dto.UserRequestDto;
import com.turi.turi0411.dto.UserResponseDto;
import com.turi.turi0411.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto.Default signup(@RequestBody UserRequestDto.SignUp signUp) {
        System.out.println("This is signup");
        return userService.save(signUp);
    }

    @GetMapping("/test")
    public UserResponseDto.Default test() {
        return UserResponseDto.Default.builder()
                .state(HttpStatus.OK.value())
                .message("테스트")
                .data(null)
                .build();
    }
}
