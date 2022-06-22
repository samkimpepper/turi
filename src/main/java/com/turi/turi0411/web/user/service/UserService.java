package com.turi.turi0411.web.user.service;

import com.turi.turi0411.config.s3.S3Uploader;
import com.turi.turi0411.exception.DuplicateAccountException;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.web.user.dto.UserInfoDto;
import com.turi.turi0411.web.user.dto.UserRequestDto;
import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.web.user.domain.User;
import com.turi.turi0411.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponseDto responseDto;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto.Default save(UserRequestDto.SignUp signUp) {
        if(userRepository.existsByEmail(signUp.getEmail())) {
            throw new DuplicateAccountException();
        }

        User user = User.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .nickname(signUp.getNickname())
                .auth("ROLE_USER")
                .build();

        userRepository.save(user);
        return responseDto.success("회원가입 성공");
    }

    @Transactional
    public ResponseDto.Data<UserInfoDto> login(UserRequestDto.Login login,
                                               HttpServletRequest request) {

        User user = userRepository.findByEmail(login.getEmail()).orElseThrow(NotFoundException::new);

        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return new ResponseDto.Data<>(HttpStatus.UNAUTHORIZED, "틀린 비밀번호");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(login.toAuthentication());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setMaxInactiveInterval(300);

        return new ResponseDto.Data<UserInfoDto>(UserInfoDto.userToDto(user), "로그인 성공");
    }

    public User findByEmail(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        return userOptional.orElse(null);
        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    // 프사, 닉네임 변경
    @Transactional
    public ResponseDto.Default updateUserInfo(MultipartFile file, HashMap<String, Object> data, String email) {
        User user = findByEmail(email);

        if(data.get("nickname") != null) {
            user.setNickname(data.get("nickname").toString());
        }
        if(!file.isEmpty()) {
            String profileImageUrl = s3Uploader.uploadFile(file);

            if(user.getProfileImageUrl() != null) {
                s3Uploader.deleteFile(user.getProfileImageUrl());
            }
            user.setProfileImageUrl(profileImageUrl);
        }

        return responseDto.success("변경 성공");
    }

    public ResponseDto.Default updatePassword(UserRequestDto.UpdatePassword updatePassword, String email) {
        User user = findByEmail(email);

        if(!passwordEncoder.matches(updatePassword.getCurrentPassword(), user.getPassword())) {
            return responseDto.fail("비밀번호 불일치", HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        return responseDto.success("비밀번호 변경 성공");
    }
}
