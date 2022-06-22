package com.turi.turi0411.web.user.dto;

import com.turi.turi0411.web.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
    private String nickname;
    private String email;
    private String profileImageUrl;

    public static UserInfoDto userToDto(User user) {
        return UserInfoDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
