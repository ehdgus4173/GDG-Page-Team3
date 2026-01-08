package com.example.backend.auth.docs;

import com.example.backend.auth.exception.SignUpErrorCode;
import com.example.backend.auth.exception.SignUpException;
import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.exception.GlobalErrorCode;
import com.example.backend.global.exception.GlobalException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;

@ExceptionDoc
public class SignUpExceptionDocs implements SwaggerExampleExceptions {

	@ExplainError("이메일 도메인이 올바르지 않습니다. @seoultech.ac.kr 도메인만 사용할 수 있습니다.")
	public GlobalCodeException 이메일_도메인_올바르지_않음 = new SignUpException(SignUpErrorCode.INVALID_EMAIL_FORMAT);

	@ExplainError("이미 존재하는 이메일입니다.")
	public GlobalCodeException 이메일_이미_존재함 = new SignUpException(SignUpErrorCode.EMAIL_ALREADY_EXISTS);

	@ExplainError("이메일 형식이 올바르지 않습니다.")
	public GlobalCodeException 이메일_형식_올바르지_않음 = new SignUpException(SignUpErrorCode.INVALID_EMAIL_FORMAT);

	@ExplainError("비밀번호 규칙을 만족하지 않습니다.")
	public GlobalCodeException 비밀번호_규칙_위반 = new SignUpException(SignUpErrorCode.INVALID_PASSWORD_FORMAT);

	@ExplainError("필수 필드가 누락되었습니다.")
	public GlobalCodeException 필수_필드_누락 = new SignUpException(SignUpErrorCode.REQUIRED_FIELD_MISSING);

	@ExplainError("밸리데이션 (검증 과정 수행 중) 발생하는 오류입니다.")
	public GlobalCodeException 검증_오류 = new GlobalException(GlobalErrorCode.ARGUMENT_NOT_VALID_ERROR);

	@ExplainError("500번대 알수없는 오류입니다. 서버 관리자에게 문의 주세요")
	public GlobalCodeException 서버_오류 = new GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);

	@ExplainError("과도한 요청을 보내셨습니다. 잠시 기다려 주세요.")
	public GlobalCodeException 과도한_요청 = new GlobalException(GlobalErrorCode.TOO_MANY_REQUEST);
}

