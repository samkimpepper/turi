package com.turi.turi0411.service;

import com.turi.turi0411.dto.UserRequestDto;
import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponseDto responseDto;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public ResponseDto.Default save(UserRequestDto.SignUp signUp) {
        User user = User.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .nickname(signUp.getNickname())
                .auth("ROLE_USER")
                .build();

        if(userRepository.existsByEmail(signUp.getEmail())) {
            return responseDto.fail("이미 존재하는 이메일", HttpStatus.CONFLICT);
        }

        userRepository.save(user);
        return ResponseDto.Default.builder()
                .state(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(null)
                .build();
    }

    @Transactional
    public ResponseDto.Default login(UserRequestDto.Login login,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(login.getEmail());
        if(!optionalUser.isPresent()) {
            return responseDto.fail("존재하지 않는 이메일", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();


        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return responseDto.fail("비번 틀림", HttpStatus.NOT_FOUND);
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(login.toAuthentication());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setMaxInactiveInterval(60);

        return responseDto.success("로그인 성공");
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

}
