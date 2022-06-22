package com.turi.turi0411.config;

import com.turi.turi0411.common.ResponseDto;
import com.turi.turi0411.exception.DuplicateAccountException;
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

    @ExceptionHandler(value = {DuplicateAccountException.class})
    protected ResponseDto.Default handleDuplicateAccountException(DuplicateAccountException ex) {
        log.error("throw DuplicateAccount Exception : {}", ex);

        return new ResponseDto.Default(ex.getCode(), "중복된 이메일");
    }


}
