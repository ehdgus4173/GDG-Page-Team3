package com.example.backend.news.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.news.exception.NewsErrorCode;
import com.example.backend.news.exception.NewsException;

@ExceptionDoc
public class NewsGetDetailExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("PathVariable(id)가 숫자가 아니거나 파싱이 불가능한 경우 발생합니다.")
    public GlobalCodeException 뉴스_ID_형식_오류 =
            new NewsException(NewsErrorCode.INVALID_NEWS_ID_FORMAT);

    @ExplainError("뉴스/소식/소식 id로 조회했으나 해당 리소스가 존재하지 않는 경우 발생합니다.")
    public GlobalCodeException 뉴스_없음 =
            new NewsException(NewsErrorCode.NEWS_NOT_FOUND);
}
