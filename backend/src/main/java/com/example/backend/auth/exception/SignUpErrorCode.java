package com.example.backend.auth.exception;

import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.example.backend.global.consts.EasyStatic.*;

/**
 * 회원가입 및 이메일 인증 관련 예외 코드
 */
@Getter
@AllArgsConstructor
public enum SignUpErrorCode implements BaseErrorCode {
	@ExplainError("이메일 인증 코드가 잘못되었습니다.")
	INVALID_CODE(BAD_REQUEST, "AUTH_400_1", "Invalid code"),

	@ExplainError("이미 존재하는 이메일입니다.")
	EMAIL_ALREADY_EXISTS(CONFLICT, "AUTH_409_1", "이미 존재하는 이메일입니다."),

	@ExplainError("이메일 인증 코드가 만료되었습니다.")
	EMAIL_VERIFICATION_CODE_EXPIRED(BAD_REQUEST, "AUTH_400_2", "이메일 인증 코드가 만료되었습니다."),

	@ExplainError("이미 인증된 이메일입니다.")
	EMAIL_ALREADY_VERIFIED(BAD_REQUEST, "AUTH_400_3", "이미 인증된 이메일입니다."),

	@ExplainError("이메일 형식이 올바르지 않습니다.")
	INVALID_EMAIL_FORMAT(BAD_REQUEST, "AUTH_400_4", "이메일 형식이 올바르지 않습니다."),

	@ExplainError("학교 이메일만 사용할 수 있습니다.")
	EMAIL_DOMAIN_INVALID(BAD_REQUEST, "AUTH_400_7", "학교 이메일만 사용할 수 있습니다."),

	@ExplainError("이메일 인증 코드를 찾을 수 없습니다.")
	EMAIL_VERIFICATION_CODE_NOT_FOUND(NOT_FOUND, "AUTH_404_1", "이메일 인증 코드를 찾을 수 없습니다."),

	@ExplainError("Firebase 토큰 검증에 실패했습니다.")
	FIREBASE_TOKEN_VERIFICATION_FAILED(UNAUTHORIZED, "AUTH_401_1", "Firebase 토큰 검증에 실패했습니다."),

	@ExplainError("Firebase 사용자 생성/연동에 실패했습니다.")
	FIREBASE_AUTH_SERVICE_ERROR(INTERNAL_SERVER, "AUTH_500_2", "Firebase 사용자 생성에 실패했습니다."),

	@ExplainError("이메일 인증이 완료되지 않았습니다.")
	EMAIL_NOT_VERIFIED(UNAUTHORIZED, "AUTH_401_3", "이메일 인증을 완료해주세요."),

	@ExplainError("필수 필드가 누락되었습니다.")
	REQUIRED_FIELD_MISSING(BAD_REQUEST, "AUTH_400_5", "필수 필드가 누락되었습니다."),

	@ExplainError("비밀번호 규칙을 만족하지 않습니다.")
	INVALID_PASSWORD_FORMAT(BAD_REQUEST, "AUTH_400_8", "비밀번호 규칙을 만족하지 않습니다.");

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
	public String getExplainError() throws NoSuchFieldException {
		Field field = this.getClass().getField(this.name());
		ExplainError annotation = field.getAnnotation(ExplainError.class);
		return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
	}
}

