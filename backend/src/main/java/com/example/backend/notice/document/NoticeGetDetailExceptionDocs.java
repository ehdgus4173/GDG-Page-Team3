package com.example.backend.notice.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.notice.exception.NoticeErrorCode;
import com.example.backend.notice.exception.NoticeException;

@ExceptionDoc
public class NoticeGetDetailExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("PathVariable(id)가 숫자가 아니거나 파싱이 불가능한 경우 발생합니다.")
    public GlobalCodeException 공지_ID_형식_오류 =
            new NoticeException(NoticeErrorCode.INVALID_NOTICE_ID_FORMAT);

    @ExplainError("공지/소식 id로 조회했으나 해당 리소스가 존재하지 않는 경우 발생합니다.")
    public GlobalCodeException 공지_없음 =
            new NoticeException(NoticeErrorCode.NOTICE_NOT_FOUND);
}
