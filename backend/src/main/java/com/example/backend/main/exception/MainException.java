package com.example.backend.main.exception;

import com.example.backend.global.exception.GlobalCodeException;

import lombok.Getter;

@Getter
public class MainException extends GlobalCodeException {
    public MainException(MainErrorCode errorCode) {
        super(errorCode);
    }

}
