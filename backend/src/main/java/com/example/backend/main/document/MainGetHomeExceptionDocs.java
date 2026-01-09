package com.example.backend.main.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.main.exception.MainErrorCode;
import com.example.backend.main.exception.MainException;

@ExceptionDoc
public class MainGetHomeExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("메인 페이지 데이터를 구성하는 과정에서 예기치 못한 서버 오류가 발생한 경우입니다.")
    public GlobalCodeException 메인페이지_내부_오류 =
            new MainException(MainErrorCode.MAIN_INTERNAL_ERROR);

    @ExplainError("공지 또는 뉴스 데이터를 조회하는 과정에서 런타임 오류가 발생한 경우입니다.")
    public GlobalCodeException 메인페이지_데이터_집계_실패 =
            new MainException(MainErrorCode.MAIN_AGGREGATION_FAILED);

    @ExplainError("엔티티를 메인 페이지 응답 DTO로 변환하는 과정에서 오류가 발생한 경우입니다.")
    public GlobalCodeException 메인페이지_응답_매핑_실패 =
            new MainException(MainErrorCode.MAIN_RESPONSE_MAPPING_FAILED);
}
