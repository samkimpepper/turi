package com.turi.turi0411.web.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class PostRequestDto {

    @Getter
    @Setter
    public static class Save {
        private String content;
        private String postType;
        private double x;
        private double y;
        private String roadAddress;
        private String jibunAddress;
        private String placeName;
        private int rating;
    }

    @Getter
    public static class Modify {
        private Long postId;
        private String postType;
        private String content;
        private double x;
        private double y;
        private String roadAddress;
        private String jibunAddress;
        private String placeName;
        private int rating;
    }
}
