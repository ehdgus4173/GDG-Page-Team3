package com.example.backend.auth.exception;

import com.example.backend.global.exception.GlobalCodeException;

public class PasswordResetException extends GlobalCodeException {
    public PasswordResetException(PasswordResetErrorCode errorCode) {
        super(errorCode);
    }
}

