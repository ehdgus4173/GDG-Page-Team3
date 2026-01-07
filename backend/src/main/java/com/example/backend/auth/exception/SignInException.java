package com.example.backend.auth.exception;

import com.example.backend.global.exception.GlobalCodeException;

public class SignInException extends GlobalCodeException {
    public SignInException(SignInErrorCode errorCode) {
        super(errorCode);
    }
}

