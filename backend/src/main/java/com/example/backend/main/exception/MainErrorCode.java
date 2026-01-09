package com.example.backend.main.exception;

import static com.example.backend.global.consts.EasyStatic.*;

import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum MainErrorCode implements BaseErrorCode {

    @ExplainError("메인 페이지 데이터를 구성하는 과정에서 예기치 못한 서버 오류가 발생한 경우입니다.")
    MAIN_INTERNAL_ERROR(INTERNAL_SERVER, "MAIN_500_1", "메인 페이지 조회 중 오류가 발생했습니다."),

    @ExplainError("공지 또는 뉴스 데이터를 조회하는 과정에서 런타임 오류가 발생한 경우입니다.")
    MAIN_AGGREGATION_FAILED(INTERNAL_SERVER, "MAIN_500_2", "메인 페이지 데이터 집계에 실패했습니다."),

    @ExplainError("엔티티를 메인 페이지 응답 DTO로 변환하는 과정에서 오류가 발생한 경우입니다.")
    MAIN_RESPONSE_MAPPING_FAILED(INTERNAL_SERVER, "MAIN_500_3", "메인 페이지 응답 생성 중 오류가 발생했습니다.");

    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .reason(reason)
                .code(code)
                .status(status)
                .build();
    }

    @Override
    public String getExplainError() {
        return this.reason;
    }
}
