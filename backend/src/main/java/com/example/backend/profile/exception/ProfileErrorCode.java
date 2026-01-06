package com.example.backend.profile.exception;

import static com.example.backend.global.consts.EasyStatic.*;

import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileErrorCode implements BaseErrorCode {

    // 404 - Not Found
    @ExplainError("내 프로필 정보를 찾을 수 없는 경우 발생합니다.")
    PROFILE_NOT_FOUND(NOT_FOUND, "PROFILE_404_1", "프로필 정보를 찾을 수 없습니다."),

    @ExplainError("비밀번호 재설정 정보를 찾을 수 없습니다.")
    PASSWORD_RESET_NOT_FOUND(NOT_FOUND, "PROFILE_404_2", "비밀번호 재설정 정보를 찾을 수 없습니다."),

    @ExplainError("프로필 이미지를 찾을 수 없습니다.")
    PROFILE_IMAGE_NOT_FOUND(NOT_FOUND, "PROFILE_404_3", "프로필 이미지를 찾을 수 없습니다."),

    // 400 - Bad Request
    @ExplainError("이름이 null 또는 blank인 경우 발생합니다.")
    NAME_REQUIRED(BAD_REQUEST, "PROFILE_400_1", "이름은 필수입니다."),

    @ExplainError("이메일이 null 또는 blank인 경우 발생합니다.")
    EMAIL_REQUIRED(BAD_REQUEST, "PROFILE_400_2", "이메일은 필수입니다."),

    @ExplainError("이메일 형식이 올바르지 않습니다.")
    INVALID_EMAIL_FORMAT(BAD_REQUEST, "PROFILE_400_3", "이메일 형식이 올바르지 않습니다."),

    @ExplainError("학번이 null 또는 blank인 경우 발생합니다.")
    STUDENT_ID_REQUIRED(BAD_REQUEST, "PROFILE_400_4", "학번은 필수입니다."),

    @ExplainError("학번 형식이 올바르지 않은 경우 발생합니다.")
    INVALID_STUDENT_ID_FORMAT(BAD_REQUEST, "PROFILE_400_5", "학번 형식이 올바르지 않습니다."),

    @ExplainError("세대(generation) 값이 유효하지 않은 경우 발생합니다.")
    INVALID_GENERATION(BAD_REQUEST, "PROFILE_400_6", "유효하지 않은 세대 정보입니다."),

    @ExplainError("역할(role) 값이 유효하지 않은 경우 발생합니다.")
    INVALID_ROLE(BAD_REQUEST, "PROFILE_400_7", "유효하지 않은 역할입니다."),

    @ExplainError("파트(part) 정보가 유효하지 않은 경우 발생합니다.")
    INVALID_PART(BAD_REQUEST, "PROFILE_400_8", "유효하지 않은 파트 정보입니다."),

    @ExplainError("학과(department) 정보가 유효하지 않은 경우 발생합니다.")
    INVALID_DEPARTMENT(BAD_REQUEST, "PROFILE_400_9", "유효하지 않은 학과 정보입니다."),

    @ExplainError("프로필 바이오 길이가 제한을 초과한 경우 발생합니다.")
    BIO_LENGTH_EXCEEDED(BAD_REQUEST, "PROFILE_400_10", "프로필 소개가 너무 깁니다."),

    @ExplainError("프로필 업데이트 시 허용되지 않는 필드가 포함된 경우 발생합니다.")
    INVALID_UPDATE_FIELD(BAD_REQUEST, "PROFILE_400_11", "수정할 수 없는 필드입니다."),

    @ExplainError("필수 프로필 정보가 누락되었습니다.")
    REQUIRED_PROFILE_INFO_MISSING(BAD_REQUEST, "PROFILE_400_12", "필수 프로필 정보가 누락되었습니다."),

    @ExplainError("현재 비밀번호가 일치하지 않아 인증에 실패한 경우 발생합니다.")
    INVALID_CURRENT_PASSWORD(BAD_REQUEST, "PROFILE_400_13", "현재 비밀번호가 일치하지 않습니다."),

    @ExplainError("새 비밀번호가 보안 규칙을 만족하지 못한 경우 발생합니다.")
    INVALID_NEW_PASSWORD(BAD_REQUEST, "PROFILE_400_14", "새 비밀번호가 규칙에 맞지 않습니다."),

    @ExplainError("새 비밀번호가 현재 비밀번호와 동일합니다.")
    SAME_AS_CURRENT_PASSWORD(BAD_REQUEST, "PROFILE_400_15", "새 비밀번호는 현재 비밀번호와 달라야 합니다."),

    @ExplainError("비밀번호 재설정 토큰이 유효하지 않습니다.")
    INVALID_PASSWORD_RESET_TOKEN(BAD_REQUEST, "PROFILE_400_16", "유효하지 않은 비밀번호 재설정 토큰입니다."),

    @ExplainError("비밀번호 재설정 토큰이 만료되었습니다.")
    PASSWORD_RESET_TOKEN_EXPIRED(BAD_REQUEST, "PROFILE_400_17", "비밀번호 재설정 토큰이 만료되었습니다."),

    @ExplainError("비밀번호 재설정 토큰이 이미 사용되었습니다.")
    PASSWORD_RESET_TOKEN_ALREADY_USED(BAD_REQUEST, "PROFILE_400_18", "이미 사용된 비밀번호 재설정 토큰입니다."),

    @ExplainError("프로필 이미지 URL이 형식이 올바르지 않은 경우 발생합니다.")
    INVALID_IMAGE_URL(BAD_REQUEST, "PROFILE_400_19", "프로필 이미지 URL 형식이 올바르지 않습니다."),

    @ExplainError("프로필 이미지 파일 크기가 제한을 초과한 경우 발생합니다.")
    IMAGE_SIZE_EXCEEDED(BAD_REQUEST, "PROFILE_400_20", "프로필 이미지 파일 크기가 너무 큽니다."),

    @ExplainError("지원하지 않는 이미지 파일 형식인 경우 발생합니다.")
    UNSUPPORTED_IMAGE_FORMAT(BAD_REQUEST, "PROFILE_400_21", "지원하지 않는 이미지 형식입니다."),

    @ExplainError("이미지 파일이 손상되었거나 유효하지 않습니다.")
    INVALID_IMAGE_FILE(BAD_REQUEST, "PROFILE_400_22", "유효하지 않은 이미지 파일입니다."),

    // 403 - Forbidden
    @ExplainError("프로필 수정 권한이 없는 경우 발생합니다.")
    PROFILE_UPDATE_FORBIDDEN(FORBIDDEN, "PROFILE_403_1", "프로필 수정 권한이 없습니다."),

    @ExplainError("프로필 접근 권한이 없는 경우 발생합니다.")
    PROFILE_ACCESS_FORBIDDEN(FORBIDDEN, "PROFILE_403_2", "프로필 접근 권한이 없습니다."),

    @ExplainError("프로필 이미지 업로드 권한이 없습니다.")
    IMAGE_UPLOAD_FORBIDDEN(FORBIDDEN, "PROFILE_403_3", "프로필 이미지 업로드 권한이 없습니다."),

    @ExplainError("비밀번호 변경 권한이 없습니다.")
    PASSWORD_CHANGE_FORBIDDEN(FORBIDDEN, "PROFILE_403_4", "비밀번호 변경 권한이 없습니다."),

    // 409 - Conflict
    @ExplainError("이메일이 이미 존재하는 경우 발생합니다.")
    EMAIL_ALREADY_EXISTS(CONFLICT, "PROFILE_409_1", "이미 등록된 이메일입니다."),

    @ExplainError("학번이 이미 존재하는 경우 발생합니다.")
    STUDENT_ID_ALREADY_EXISTS(CONFLICT, "PROFILE_409_2", "이미 등록된 학번입니다."),

    @ExplainError("프로필 업데이트 요청이 충돌했습니다.")
    PROFILE_UPDATE_CONFLICT(CONFLICT, "PROFILE_409_3", "프로필 업데이트 요청이 충돌했습니다. 다시 시도해주세요."),

    // 500 - Internal Server Error
    @ExplainError("프로필 이미지 업로드 중 서버 오류가 발생한 경우")
    IMAGE_UPLOAD_FAILED(INTERNAL_SERVER, "PROFILE_500_1", "이미지 업로드 중 오류가 발생했습니다."),

    @ExplainError("프로필 이미지 삭제 중 서버 오류가 발생한 경우")
    IMAGE_DELETE_FAILED(INTERNAL_SERVER, "PROFILE_500_2", "이미지 삭제 중 오류가 발생했습니다."),

    @ExplainError("프로필 정보 조회/업데이트 중 알 수 없는 서버 오류가 발생한 경우")
    PROFILE_INTERNAL_ERROR(INTERNAL_SERVER, "PROFILE_500_3", "프로필 처리 중 오류가 발생했습니다."),

    @ExplainError("비밀번호 암호화 중 서버 오류가 발생한 경우")
    PASSWORD_ENCRYPTION_FAILED(INTERNAL_SERVER, "PROFILE_500_4", "비밀번호 처리 중 오류가 발생했습니다."),

    @ExplainError("프로필 이미지 처리 중 서버 오류가 발생한 경우")
    IMAGE_PROCESSING_FAILED(INTERNAL_SERVER, "PROFILE_500_5", "이미지 처리 중 오류가 발생했습니다.");

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