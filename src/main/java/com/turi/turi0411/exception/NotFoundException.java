package com.turi.turi0411.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private String code;

    public NotFoundException() {
        super();
        code = "NOT_FOUND";
    }
}
