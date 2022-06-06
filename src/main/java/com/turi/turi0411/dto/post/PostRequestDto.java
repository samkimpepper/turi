package com.turi.turi0411.dto.post;

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
    public static class Delete {
        private Long postId;
    }
}
