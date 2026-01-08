package com.example.backend.auth.exception;

import com.example.backend.global.exception.GlobalCodeException;

public class SignUpException extends GlobalCodeException {
    public SignUpException(SignUpErrorCode errorCode) {
        super(errorCode);
    }
}

