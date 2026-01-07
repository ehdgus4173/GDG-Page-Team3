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
public class SignOutExceptionDocs implements SwaggerExampleExceptions {

	@ExplainError("토큰이 만료되었습니다.")
	public GlobalCodeException 토큰_만료 = new SignInException(SignInErrorCode.TOKEN_EXPIRED);

	@ExplainError("유효하지 않은 토큰입니다.")
	public GlobalCodeException 토큰_유효하지_않음 = new SignInException(SignInErrorCode.INVALID_TOKEN);

	@ExplainError("로그아웃에 실패했습니다.")
	public GlobalCodeException 로그아웃_실패 = new SignInException(SignInErrorCode.LOGOUT_FAILED);

	@ExplainError("이미 로그아웃된 토큰입니다.")
	public GlobalCodeException 이미_로그아웃된_토큰 = new SignInException(SignInErrorCode.ALREADY_LOGGED_OUT);

	@ExplainError("밸리데이션 (검증 과정 수행 중) 발생하는 오류입니다.")
	public GlobalCodeException 검증_오류 = new GlobalException(GlobalErrorCode.ARGUMENT_NOT_VALID_ERROR);

	@ExplainError("500번대 알수없는 오류입니다. 서버 관리자에게 문의 주세요")
	public GlobalCodeException 서버_오류 = new GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);

	@ExplainError("과도한 요청을 보내셨습니다. 잠시 기다려 주세요.")
	public GlobalCodeException 과도한_요청 = new GlobalException(GlobalErrorCode.TOO_MANY_REQUEST);
}

