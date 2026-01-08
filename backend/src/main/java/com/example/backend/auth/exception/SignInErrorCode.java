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
 * 로그인 및 로그아웃 관련 예외 코드
 */
@Getter
@AllArgsConstructor
public enum SignInErrorCode implements BaseErrorCode {
	@ExplainError("이메일 또는 비밀번호가 일치하지 않습니다.")
	INVALID_CREDENTIALS(UNAUTHORIZED, "AUTH_401_2", "이메일 또는 비밀번호가 일치하지 않습니다."),

	@ExplainError("존재하지 않는 이메일입니다.")
	EMAIL_NOT_FOUND(NOT_FOUND, "AUTH_404_2", "존재하지 않는 이메일입니다."),

	@ExplainError("이메일이 인증되지 않았습니다.")
	EMAIL_NOT_VERIFIED(UNAUTHORIZED, "AUTH_401_3", "이메일이 인증되지 않았습니다."),

	@ExplainError("계정이 비활성화되었습니다.")
	ACCOUNT_DISABLED(FORBIDDEN, "AUTH_403_1", "계정이 비활성화되었습니다."),

	@ExplainError("비밀번호가 올바르지 않습니다.")
	INVALID_PASSWORD(UNAUTHORIZED, "AUTH_401_4", "비밀번호가 올바르지 않습니다."),

	@ExplainError("로그인 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.")
	TOO_MANY_LOGIN_ATTEMPTS(429, "AUTH_429_1", "로그인 요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

	@ExplainError("토큰이 만료되었습니다.")
	TOKEN_EXPIRED(UNAUTHORIZED, "AUTH_401_5", "토큰이 만료되었습니다."),

	@ExplainError("유효하지 않은 토큰입니다.")
	INVALID_TOKEN(UNAUTHORIZED, "AUTH_401_6", "유효하지 않은 토큰입니다."),

	@ExplainError("로그아웃에 실패했습니다.")
	LOGOUT_FAILED(INTERNAL_SERVER, "AUTH_500_1", "로그아웃에 실패했습니다."),

	@ExplainError("이미 로그아웃된 토큰입니다.")
	ALREADY_LOGGED_OUT(UNAUTHORIZED, "AUTH_401_7", "이미 로그아웃된 토큰입니다.");

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

