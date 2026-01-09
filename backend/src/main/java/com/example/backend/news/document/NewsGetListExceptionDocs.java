package com.example.backend.news.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.news.exception.NewsErrorCode;
import com.example.backend.news.exception.NewsException;

@ExceptionDoc
public class NewsGetListExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("page가 0 미만이거나 허용되지 않는 값인 경우 발생합니다.")
    public GlobalCodeException 페이지_값_오류 =
            new NewsException(NewsErrorCode.INVALID_PAGE);

    @ExplainError("size가 1 미만이거나 서버 정책의 최대 size를 초과한 경우 발생합니다.")
    public GlobalCodeException 페이지_크기_값_오류 =
            new NewsException(NewsErrorCode.INVALID_SIZE);

    @ExplainError("generation 필터가 음수이거나 허용 범위를 벗어난 경우 발생합니다.")
    public GlobalCodeException 기수_필터_값_오류 =
            new NewsException(NewsErrorCode.INVALID_GENERATION_FILTER);
}
