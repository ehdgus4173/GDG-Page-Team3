package com.example.backend.profile.exception;

import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class ProfileException extends GlobalCodeException {
    public ProfileException(ProfileErrorCode errorCode) {
        super(errorCode);
    }
}