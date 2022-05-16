package com.turi.turi0411.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class PostRequestDto {

    @Getter
    @Setter
    public static class Save {
        private String content;
        private String type;
        private MultipartFile[] images;
        private float x;
        private float y;
        private String roadAddress;
        //private String jibunAddress;
        private String placeName;
    }
}
