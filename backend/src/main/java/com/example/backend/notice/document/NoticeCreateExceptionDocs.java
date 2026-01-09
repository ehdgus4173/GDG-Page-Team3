package com.example.backend.notice.document;

import com.example.backend.global.annotation.ExceptionDoc;
import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.exception.GlobalCodeException;
import com.example.backend.global.interfaces.SwaggerExampleExceptions;
import com.example.backend.notice.exception.NoticeErrorCode;
import com.example.backend.notice.exception.NoticeException;

@ExceptionDoc
public class NoticeCreateExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("공지 제목(title)이 null/blank인 경우 발생합니다.")
    public GlobalCodeException 제목_필수 =
            new NoticeException(NoticeErrorCode.TITLE_REQUIRED);

    @ExplainError("공지 본문(content)이 null/blank인 경우 발생합니다.")
    public GlobalCodeException 내용_필수 =
            new NoticeException(NoticeErrorCode.CONTENT_REQUIRED);

    @ExplainError("제목 길이가 허용 최대치를 초과한 경우 발생합니다. (200자)")
    public GlobalCodeException 제목_길이_초과 =
            new NoticeException(NoticeErrorCode.TITLE_TOO_LONG);

    @ExplainError("작성자 식별자(authorId)가 누락되었거나 유효하지 않은 경우 발생합니다.")
    public GlobalCodeException 작성자_오류 =
            new NoticeException(NoticeErrorCode.AUTHOR_ID_INVALID);

    @ExplainError("thumbnailUrl이 존재하지만 URL 형식이 올바르지 않은 경우 발생합니다.")
    public GlobalCodeException 썸네일_URL_형식_오류 =
            new NoticeException(NoticeErrorCode.INVALID_THUMBNAIL_URL);

    @ExplainError("첨부 파일 리스트는 존재하지만 내부 요소가 null이거나 필수 필드가 비어있는 경우 발생합니다.")
    public GlobalCodeException 첨부파일_페이로드_오류 =
            new NoticeException(NoticeErrorCode.INVALID_FILE_PAYLOAD);

    @ExplainError("첨부 파일 URL이 비어있거나 URL 형식이 올바르지 않은 경우 발생합니다.")
    public GlobalCodeException 첨부파일_URL_형식_오류 =
            new NoticeException(NoticeErrorCode.INVALID_FILE_URL);

    @ExplainError("첨부 파일 타입(enum)이 유효하지 않거나 서버가 지원하지 않는 타입인 경우 발생합니다.")
    public GlobalCodeException 첨부파일_타입_오류 =
            new NoticeException(NoticeErrorCode.INVALID_FILE_TYPE);

    @ExplainError("동일 조건에서 중복 생성 등 도메인 무결성 정책에 위배되는 경우 발생합니다.")
    public GlobalCodeException 생성_충돌 =
            new NoticeException(NoticeErrorCode.NOTICE_CONFLICT);
}
