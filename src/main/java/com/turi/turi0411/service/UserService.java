package com.turi.turi0411.service;

import com.turi.turi0411.dto.UserRequestDto;
import com.turi.turi0411.dto.UserResponseDto;
import com.turi.turi0411.entity.User;
import com.turi.turi0411.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto.Default save(UserRequestDto.SignUp signUp) {
        User user = User.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .nickname(signUp.getNickname())
                .auth("ROLE_USER")
                .build();

        userRepository.save(user);
        return UserResponseDto.Default.builder()
                .state(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(null)
                .build();
    }

}
