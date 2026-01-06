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

    @ExplainError("이메일 형식이 올바르지 않습니다.")
    INVALID_EMAIL_FORMAT(BAD_REQUEST, "MEMBER_400_5", "이메일 형식이 올바르지 않습니다."),

    @ExplainError("비밀번호 형식이 올바르지 않습니다.")
    INVALID_PASSWORD_FORMAT(BAD_REQUEST, "MEMBER_400_6", "비밀번호는 8자 이상이어야 합니다."),

    @ExplainError("이름이 null 또는 blank인 경우 발생합니다.")
    NAME_REQUIRED(BAD_REQUEST, "MEMBER_400_7", "이름은 필수입니다."),

    @ExplainError("학번 형식이 올바르지 않습니다.")
    INVALID_STUDENT_ID_FORMAT(BAD_REQUEST, "MEMBER_400_8", "학번 형식이 올바르지 않습니다."),

    @ExplainError("세대(generation) 값이 유효하지 않습니다.")
    INVALID_GENERATION(BAD_REQUEST, "MEMBER_400_9", "유효하지 않은 세대 정보입니다."),

    @ExplainError("역할(role) 값이 유효하지 않습니다.")
    INVALID_ROLE(BAD_REQUEST, "MEMBER_400_10", "유효하지 않은 역할입니다."),

    @ExplainError("파트(part) 정보가 유효하지 않습니다.")
    INVALID_PART(BAD_REQUEST, "MEMBER_400_11", "유효하지 않은 파트 정보입니다."),

    @ExplainError("학과(department) 정보가 유효하지 않습니다.")
    INVALID_DEPARTMENT(BAD_REQUEST, "MEMBER_400_12", "유효하지 않은 학과 정보입니다."),

    @ExplainError("사용자 상태(status)가 유효하지 않습니다.")
    INVALID_STATUS(BAD_REQUEST, "MEMBER_400_13", "유효하지 않은 사용자 상태입니다."),



    // 403 - Forbidden
    @ExplainError("이메일 인증이 완료되지 않았습니다.")
    EMAIL_NOT_VERIFIED(FORBIDDEN, "MEMBER_403_1", "이메일 인증이 필요합니다."),

    @ExplainError("회원 목록 조회 권한이 없습니다.")
    MEMBER_LIST_ACCESS_FORBIDDEN(FORBIDDEN, "MEMBER_403_2", "회원 목록 조회 권한이 없습니다."),

    @ExplainError("회원 상세 정보 조회 권한이 없습니다.")
    MEMBER_DETAIL_ACCESS_FORBIDDEN(FORBIDDEN, "MEMBER_403_3", "회원 정보 조회 권한이 없습니다."),

    @ExplainError("비활성화된 계정입니다.")
    ACCOUNT_DISABLED(FORBIDDEN, "MEMBER_403_4", "비활성화된 계정입니다. 관리자에게 문의하세요."),

    @ExplainError("정지된 계정입니다.")
    ACCOUNT_SUSPENDED(FORBIDDEN, "MEMBER_403_5", "정지된 계정입니다. 관리자에게 문의하세요."),

    @ExplainError("관리자 권한이 필요합니다.")
    ADMIN_PERMISSION_REQUIRED(FORBIDDEN, "MEMBER_403_6", "관리자 권한이 필요합니다."),


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