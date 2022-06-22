package com.turi.turi0411.web.user.controller;

import com.turi.turi0411.web.user.dto.UserInfoDto;
import com.turi.turi0411.web.user.dto.UserRequestDto;
import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

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
    public ResponseDto.Data<UserInfoDto> login(@RequestBody UserRequestDto.Login login,
                                               HttpServletRequest request) {
        return userService.login(login, request);

    }

    @GetMapping("/logout")
    public ResponseDto.Default logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            SecurityContextHolder.clearContext();
        }
        return responseDto.success("로그아웃 성공");
    }

    @PutMapping("/update-info")
    public ResponseDto.Default updateUserInfo(@RequestPart(required=false) MultipartFile file, @RequestPart(required=false) HashMap<String, Object> data) {
        System.out.println("여기는 update-info");
        //String nickname = data.get("nickname").toString();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateUserInfo(file, data, email);
    }

    @GetMapping("/update-password")
    public ResponseDto.Default updatePassword(@RequestBody UserRequestDto.UpdatePassword updatePassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updatePassword(updatePassword, email);
    }

//
//    @GetMapping("/post/{email}")
//    public ResponseDto.DataList<T> getUserPostsList(@PathVariable(name="email") String email) {
//
//    }


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
    public String sessionTest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            System.out.println(name + ":" + request.getHeader(name));
        }

        return "여기는 로그인한 사람만 들어올 수 있는 페이지야";
    }
}
