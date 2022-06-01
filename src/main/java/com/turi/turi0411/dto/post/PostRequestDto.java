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
        //private MultipartFile[] images;
        private float x;
        private float y;
        private String roadAddress;
        private String jibunAddress;
        private String placeName;
    }

    @Getter
    public static class Delete {
        private Long postId;
    }
}
