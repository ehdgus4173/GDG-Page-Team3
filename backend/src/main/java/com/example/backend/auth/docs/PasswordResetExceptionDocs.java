package com.example.backend.auth.docs;

import com.example.backend.auth.exception.PasswordResetErrorCode;
import com.example.backend.auth.exception.PasswordResetException;
import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.exception.GlobalErrorCode;
import com.example.backend.global.exception.GlobalException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;

@ExceptionDoc
public class PasswordResetExceptionDocs implements SwaggerExampleExceptions {

	@ExplainError("비밀번호 정책을 만족하지 않습니다.")
	public GlobalCodeException 비밀번호_정책_위반 = new PasswordResetException(PasswordResetErrorCode.PASSWORD_POLICY_VIOLATION);

	@ExplainError("이전 비밀번호와 동일한 비밀번호입니다.")
	public GlobalCodeException 이전_비밀번호와_동일 = new PasswordResetException(PasswordResetErrorCode.SAME_AS_OLD_PASSWORD);

	@ExplainError("존재하지 않는 사용자입니다.")
	public GlobalCodeException 사용자_없음 = new PasswordResetException(PasswordResetErrorCode.USER_NOT_FOUND);

	@ExplainError("이메일 도메인이 올바르지 않습니다. @seoultech.ac.kr 도메인만 사용할 수 있습니다.")
	public GlobalCodeException 이메일_도메인_올바르지_않음 = new PasswordResetException(PasswordResetErrorCode.INVALID_EMAIL_DOMAIN);

	@ExplainError("밸리데이션 (검증 과정 수행 중) 발생하는 오류입니다.")
	public GlobalCodeException 검증_오류 = new GlobalException(GlobalErrorCode.ARGUMENT_NOT_VALID_ERROR);

	@ExplainError("500번대 알수없는 오류입니다. 서버 관리자에게 문의 주세요")
	public GlobalCodeException 서버_오류 = new GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);

	@ExplainError("과도한 요청을 보내셨습니다. 잠시 기다려 주세요.")
	public GlobalCodeException 과도한_요청 = new GlobalException(GlobalErrorCode.TOO_MANY_REQUEST);
}

