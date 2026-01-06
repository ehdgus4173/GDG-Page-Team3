package com.example.backend.member.exception;

import com.example.backend.global.exception.GlobalCodeException;


public class MemberException extends GlobalCodeException {
    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
