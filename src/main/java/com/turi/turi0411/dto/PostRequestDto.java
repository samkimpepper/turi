package com.turi.turi0411.dto;

import lombok.Getter;
import lombok.Setter;

public class PostRequestDto {

    @Getter
    @Setter
    public static class Save {
        private String content;
    }
}
