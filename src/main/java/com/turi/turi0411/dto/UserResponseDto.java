package com.turi.turi0411.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Default {
        private int state;
        private String message;
        private Object data;


    }
}
