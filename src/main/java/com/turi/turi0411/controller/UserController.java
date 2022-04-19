package com.turi.turi0411.controller;

import com.turi.turi0411.dto.UserRequestDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final ResponseDto responseDto;

    @PostMapping("/signup")
    public ResponseDto.Default signup(@RequestBody UserRequestDto.SignUp signUp) {
        System.out.println("This is signup");
        return userService.save(signUp);
    }

    @PostMapping("/login")
    public ResponseDto.Default login(@RequestBody UserRequestDto.Login login,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        return userService.login(login, request, response);

    }

    @PostMapping("/logout")
    public ResponseDto.Default logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            SecurityContextHolder.clearContext();
        }
        return responseDto.success("로그아웃 성공");
    }

    @GetMapping("/test")
    public ResponseDto.Default test(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.info("유저컨트롤러!!!! 세션 !!! " + session.getAttribute("loginUser"));
        log.info("시큐리티컨텍스트 확인!!" + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseDto.Default.builder()
                .state(HttpStatus.OK.value())
                .message("테스트")
                .data(null)
                .build();
    }

    @GetMapping("/session-test")
    public String sessionTest() {
        return "여기는 로그인한 사람만 들어올 수 있는 페이지야";
    }
}
