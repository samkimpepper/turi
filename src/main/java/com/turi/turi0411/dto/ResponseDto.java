package com.turi.turi0411.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Getter
    public static class Data<T> {
        private int state;
        private String message;
        private T data;

        public Data(T data, String msg) {
            state = HttpStatus.OK.value();
            message = msg;
            this.data = data;
        }
    }

    @Getter
    public static class DataList<T> {
        private int state;
        private String message;
        private List<T> data;
        private int totalCount;

        public DataList(List<T> data, String msg) {
            state = HttpStatus.OK.value();
            message = msg;
            this.data = data;
            totalCount = data.size();
        }
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
