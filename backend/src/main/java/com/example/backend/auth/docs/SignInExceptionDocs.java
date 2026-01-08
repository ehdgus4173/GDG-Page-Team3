package com.example.backend.auth.docs;

import com.example.backend.auth.exception.SignInErrorCode;
import com.example.backend.auth.exception.SignInException;
import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.exception.GlobalErrorCode;
import com.example.backend.global.exception.GlobalException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;

@ExceptionDoc
public class SignInExceptionDocs implements SwaggerExampleExceptions {

	@ExplainError("이메일 또는 비밀번호가 일치하지 않습니다.")
	public GlobalCodeException 자격증명_일치하지_않음 = new SignInException(SignInErrorCode.INVALID_CREDENTIALS);

	@ExplainError("존재하지 않는 이메일입니다.")
	public GlobalCodeException 이메일_없음 = new SignInException(SignInErrorCode.EMAIL_NOT_FOUND);

	@ExplainError("이메일이 인증되지 않았습니다. 이메일 인증을 완료해주세요.")
	public GlobalCodeException 이메일_미인증 = new SignInException(SignInErrorCode.EMAIL_NOT_VERIFIED);

	@ExplainError("계정이 비활성화되었습니다.")
	public GlobalCodeException 계정_비활성화 = new SignInException(SignInErrorCode.ACCOUNT_DISABLED);

	@ExplainError("비밀번호가 올바르지 않습니다.")
	public GlobalCodeException 비밀번호_올바르지_않음 = new SignInException(SignInErrorCode.INVALID_PASSWORD);

	@ExplainError("로그인 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.")
	public GlobalCodeException 로그인_시도_과다 = new SignInException(SignInErrorCode.TOO_MANY_LOGIN_ATTEMPTS);

	@ExplainError("밸리데이션 (검증 과정 수행 중) 발생하는 오류입니다.")
	public GlobalCodeException 검증_오류 = new GlobalException(GlobalErrorCode.ARGUMENT_NOT_VALID_ERROR);

	@ExplainError("500번대 알수없는 오류입니다. 서버 관리자에게 문의 주세요")
	public GlobalCodeException 서버_오류 = new GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);

	@ExplainError("과도한 요청을 보내셨습니다. 잠시 기다려 주세요.")
	public GlobalCodeException 과도한_요청 = new GlobalException(GlobalErrorCode.TOO_MANY_REQUEST);
}

