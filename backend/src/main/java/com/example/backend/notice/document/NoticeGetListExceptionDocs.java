package com.example.backend.notice.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.notice.exception.NoticeErrorCode;
import com.example.backend.notice.exception.NoticeException;

@ExceptionDoc
public class NoticeGetListExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("page가 0 미만이거나 허용되지 않는 값인 경우 발생합니다.")
    public GlobalCodeException 페이지_값_오류 =
            new NoticeException(NoticeErrorCode.INVALID_PAGE);

    @ExplainError("size가 1 미만이거나 서버 정책의 최대 size를 초과한 경우 발생합니다.")
    public GlobalCodeException 페이지_크기_값_오류 =
            new NoticeException(NoticeErrorCode.INVALID_SIZE);
}
