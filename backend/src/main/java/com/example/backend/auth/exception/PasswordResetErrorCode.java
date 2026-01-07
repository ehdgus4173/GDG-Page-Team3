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
 * 비밀번호 재설정 관련 예외 코드
 */
@Getter
@AllArgsConstructor
public enum PasswordResetErrorCode implements BaseErrorCode {
	@ExplainError("이메일 도메인이 올바르지 않습니다. @seoultech.ac.kr 도메인만 사용 가능합니다.")
	INVALID_EMAIL_DOMAIN(BAD_REQUEST, "AUTH_400_7", "이메일 도메인이 올바르지 않습니다."),

	@ExplainError("비밀번호 재설정 인증이 완료되지 않았습니다.")
	PASSWORD_RESET_NOT_VERIFIED(BAD_REQUEST, "AUTH_400_8", "비밀번호 재설정 인증이 완료되지 않았습니다."),

	@ExplainError("비밀번호 재설정 토큰이 만료되었습니다.")
	PASSWORD_RESET_TOKEN_EXPIRED(BAD_REQUEST, "AUTH_400_9", "비밀번호 재설정 토큰이 만료되었습니다."),

	@ExplainError("비밀번호 재설정 토큰을 찾을 수 없습니다.")
	PASSWORD_RESET_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH_404_3", "비밀번호 재설정 토큰을 찾을 수 없습니다."),

	@ExplainError("이미 사용된 비밀번호 재설정 토큰입니다.")
	PASSWORD_RESET_TOKEN_ALREADY_USED(BAD_REQUEST, "AUTH_400_10", "이미 사용된 비밀번호 재설정 토큰입니다."),

	@ExplainError("Firebase 비밀번호 재설정 이메일 발송에 실패했습니다.")
	FIREBASE_PASSWORD_RESET_EMAIL_FAILED(INTERNAL_SERVER, "AUTH_500_2", "비밀번호 재설정 이메일 발송에 실패했습니다."),

	@ExplainError("비밀번호 정책을 만족하지 않습니다.")
	PASSWORD_POLICY_VIOLATION(BAD_REQUEST, "AUTH_400_11", "비밀번호 정책을 만족하지 않습니다."),

	@ExplainError("이전 비밀번호와 동일한 비밀번호입니다.")
	SAME_AS_OLD_PASSWORD(BAD_REQUEST, "AUTH_400_12", "이전 비밀번호와 동일한 비밀번호입니다."),

	@ExplainError("존재하지 않는 사용자입니다.")
	USER_NOT_FOUND(NOT_FOUND, "AUTH_404_4", "존재하지 않는 사용자입니다.");

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

