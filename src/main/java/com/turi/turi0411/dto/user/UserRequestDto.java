package com.turi.turi0411.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp {
        private String email;
        private String password;
        private String nickname;
    }

    @Getter
    @Setter
    public static class Login {
        private String email;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class UpdateUserInfo {
        private String nickname;
        private MultipartFile profileImage;
    }

    @Getter
    @Setter
    public static class UpdatePassword {
        private String currentPassword;
        private String newPassword;
    }
}
