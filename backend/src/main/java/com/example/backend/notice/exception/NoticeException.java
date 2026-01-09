package com.example.backend.notice.exception;

import com.example.backend.global.exception.GlobalCodeException;
import lombok.Getter;

@Getter
public class NoticeException extends GlobalCodeException {

    public NoticeException(NoticeErrorCode errorCode) {
        super(errorCode);
    }
}
