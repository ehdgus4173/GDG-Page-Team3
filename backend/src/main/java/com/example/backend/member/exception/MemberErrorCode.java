package com.example.backend.member.exception;

import static com.example.backend.global.consts.EasyStatic.*;

import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    // 404 - Not Found
    @ExplainError("존재하지 않는 회원입니다.")
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER_404_1", "존재하지 않는 회원입니다."),

    @ExplainError("회원 목록 조회 시 결과가 없습니다.")
    MEMBER_LIST_EMPTY(NOT_FOUND, "MEMBER_404_2", "조회된 회원이 없습니다."),

    // 400 - Bad Request
    @ExplainError("회원 ID 형식이 올바르지 않습니다.")
    INVALID_MEMBER_ID_FORMAT(BAD_REQUEST, "MEMBER_400_1", "회원 ID 형식이 올바르지 않습니다."),

    @ExplainError("회원 조회 필터 파라미터가 유효하지 않습니다.")
    INVALID_FILTER_PARAMETER(BAD_REQUEST, "MEMBER_400_2", "유효하지 않은 필터 조건입니다."),

    @ExplainError("페이지 번호가 유효하지 않습니다.")
    INVALID_PAGE_NUMBER(BAD_REQUEST, "MEMBER_400_3", "유효하지 않은 페이지 번호입니다."),

    @ExplainError("정렬 조건이 유효하지 않습니다.")
    INVALID_SORT_PARAMETER(BAD_REQUEST, "MEMBER_400_4", "유효하지 않은 정렬 조건입니다."),


    // 403 - Forbidden
    @ExplainError("회원 목록 조회 권한이 없습니다.")
    MEMBER_LIST_ACCESS_FORBIDDEN(FORBIDDEN, "MEMBER_403_2", "회원 목록 조회 권한이 없습니다."),

    @ExplainError("회원 상세 정보 조회 권한이 없습니다.")
    MEMBER_DETAIL_ACCESS_FORBIDDEN(FORBIDDEN, "MEMBER_403_3", "회원 정보 조회 권한이 없습니다."),

    // 500 - Internal Server Error
    @ExplainError("회원 정보 처리 중 서버 오류가 발생했습니다.")
    MEMBER_INTERNAL_ERROR(INTERNAL_SERVER, "MEMBER_500_3", "회원 정보 처리 중 오류가 발생했습니다."),

    @ExplainError("회원 목록 조회 중 서버 오류가 발생했습니다.")
    MEMBER_LIST_FETCH_FAILED(INTERNAL_SERVER, "MEMBER_500_5", "회원 목록 조회 중 오류가 발생했습니다.");

    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(this.status)
                .code(this.code)
                .reason(this.reason)
                .build();
    }

    @Override
    public String getExplainError() {
        return this.reason;
    }
}