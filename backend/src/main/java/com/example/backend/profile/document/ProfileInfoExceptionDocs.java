package com.example.backend.profile.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.profile.exception.ProfileErrorCode;
import com.example.backend.profile.exception.ProfileException;

@ExceptionDoc
public class ProfileInfoExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("내 프로필 정보를 찾을 수 없는 경우 발생합니다.")
    public GlobalCodeException 프로필_찾을수_없음 = new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND);

    @ExplainError("프로필 소개(Bio)가 제한 길이를 초과했을 때 발생합니다.")
    public GlobalCodeException 소개_길이_초과 = new ProfileException(ProfileErrorCode.BIO_LENGTH_EXCEEDED);

    @ExplainError("수정할 수 없는 필드(예: 변경 불가능한 계정 정보 등)를 수정하려 할 때 발생합니다.")
    public GlobalCodeException 수정_불가_필드 = new ProfileException(ProfileErrorCode.INVALID_UPDATE_FIELD);

    @ExplainError("프로필 수정 권한이 없는 경우 발생합니다.")
    public GlobalCodeException 프로필_수정_권한_없음 = new ProfileException(ProfileErrorCode.PROFILE_UPDATE_FORBIDDEN);
}
