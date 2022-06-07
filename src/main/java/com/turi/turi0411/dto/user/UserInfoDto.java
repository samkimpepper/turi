package com.turi.turi0411.dto.user;

import com.turi.turi0411.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

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
