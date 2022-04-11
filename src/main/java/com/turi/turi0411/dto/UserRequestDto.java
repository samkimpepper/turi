package com.turi.turi0411.dto;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp {
        private String email;
        private String password;
        private String nickname;
    }
}
