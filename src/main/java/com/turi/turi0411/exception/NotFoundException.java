package com.turi.turi0411.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private int code;
    private String message;

    public NotFoundException(String message) {
        super(message);

        this.message = message;
        code = HttpStatus.NOT_FOUND.value();
    }
}
