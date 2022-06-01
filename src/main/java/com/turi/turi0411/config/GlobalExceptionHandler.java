package com.turi.turi0411.config;

import com.turi.turi0411.dto.ResponseDto;
import com.turi.turi0411.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseDto.Default resourceNotFoundException(NotFoundException ex) {
        log.error("throw NotFoundException : {}", ex);

        return new ResponseDto.Default(ex.getCode(), ex.getMessage(), null);
    }

}
