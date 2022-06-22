package com.turi.turi0411.exception;

import lombok.Getter;

@Getter
public class DuplicateAccountException extends RuntimeException{
    private String code;

    public DuplicateAccountException() {
        super();
        code = "ACCOUNT_DUPLICATED";
    }
}
