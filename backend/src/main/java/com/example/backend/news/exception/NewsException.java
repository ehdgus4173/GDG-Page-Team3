package com.example.backend.news.exception;

import com.example.backend.global.exception.GlobalCodeException;
import lombok.Getter;

@Getter
public class NewsException extends GlobalCodeException {

    public NewsException(NewsErrorCode errorCode) {
        super(errorCode);
    }
}
