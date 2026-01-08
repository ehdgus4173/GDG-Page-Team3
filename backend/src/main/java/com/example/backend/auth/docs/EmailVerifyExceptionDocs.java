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
public class EmailVerifyExceptionDocs implements SwaggerExampleExceptions {

	@ExplainError("Firebase 토큰 검증에 실패했습니다. 유효하지 않은 인증 코드입니다.")
	public GlobalCodeException 인증_코드_잘못됨 = new SignUpException(SignUpErrorCode.INVALID_CODE);

	@ExplainError("Firebase Admin SDK 토큰 검증에 실패했습니다.")
	public GlobalCodeException Firebase_토큰_검증_실패 = new SignUpException(SignUpErrorCode.FIREBASE_TOKEN_VERIFICATION_FAILED);

	@ExplainError("이메일 인증 코드를 찾을 수 없습니다.")
	public GlobalCodeException 인증_코드_없음 = new SignUpException(SignUpErrorCode.EMAIL_VERIFICATION_CODE_NOT_FOUND);

	@ExplainError("이메일 인증 코드가 만료되었습니다.")
	public GlobalCodeException 인증_코드_만료 = new SignUpException(SignUpErrorCode.EMAIL_VERIFICATION_CODE_EXPIRED);

	@ExplainError("이미 인증된 이메일입니다.")
	public GlobalCodeException 이미_인증된_이메일 = new SignUpException(SignUpErrorCode.EMAIL_ALREADY_VERIFIED);

	@ExplainError("이메일 형식이 올바르지 않습니다.")
	public GlobalCodeException 이메일_형식_올바르지_않음 = new SignUpException(SignUpErrorCode.INVALID_EMAIL_FORMAT);

	@ExplainError("필수 필드가 누락되었습니다. 이메일을 입력해주세요.")
	public GlobalCodeException 필수_필드_누락 = new SignUpException(SignUpErrorCode.REQUIRED_FIELD_MISSING);

	@ExplainError("밸리데이션 (검증 과정 수행 중) 발생하는 오류입니다.")
	public GlobalCodeException 검증_오류 = new GlobalException(GlobalErrorCode.ARGUMENT_NOT_VALID_ERROR);

	@ExplainError("500번대 알수없는 오류입니다. 서버 관리자에게 문의 주세요")
	public GlobalCodeException 서버_오류 = new GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);

	@ExplainError("과도한 요청을 보내셨습니다. 잠시 기다려 주세요.")
	public GlobalCodeException 과도한_요청 = new GlobalException(GlobalErrorCode.TOO_MANY_REQUEST);
}
