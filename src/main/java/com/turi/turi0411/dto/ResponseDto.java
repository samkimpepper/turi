package com.turi.turi0411.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Default {
        private int state;
        private String message;
        private Object data;
    }

    public Default success(String msg) {
        return Default.builder()
                .state(HttpStatus.OK.value())
                .message(msg)
                .data(null)
                .build();
    }

    public Default fail(String msg, HttpStatus status) {
        return Default.builder()
                .state(status.value())
                .message(msg)
                .data(null)
                .build();
    }
}
