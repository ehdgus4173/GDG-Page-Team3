package com.example.backend.news.exception;

import static com.example.backend.global.consts.EasyStatic.*;

import com.example.backend.global.annotation.ExplainError;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum NewsErrorCode implements BaseErrorCode {

    @ExplainError("뉴스/소식/소식 id로 조회했으나 해당 리소스가 존재하지 않는 경우 발생합니다.")
    NEWS_NOT_FOUND(NOT_FOUND, "NEWS_404_1", "해당 뉴스/소식를 찾을 수 없습니다."),

    @ExplainError("PathVariable(id)가 숫자가 아니거나 파싱이 불가능한 경우 발생합니다.")
    INVALID_NEWS_ID_FORMAT(BAD_REQUEST, "NEWS_400_1", "뉴스/소식 ID 형식이 올바르지 않습니다."),

    @ExplainError("page가 0 미만이거나 허용되지 않는 값인 경우 발생합니다.")
    INVALID_PAGE(BAD_REQUEST, "NEWS_400_2", "페이지 값이 올바르지 않습니다."),

    @ExplainError("size가 1 미만이거나 서버 정책의 최대 size를 초과한 경우 발생합니다.")
    INVALID_SIZE(BAD_REQUEST, "NEWS_400_3", "페이지 크기 값이 올바르지 않습니다."),

//    필요 없을 것 같기도.. 어차피 버튼 형식이니까?
    @ExplainError("generation 필터가 음수이거나 허용 범위를 벗어난 경우 발생합니다.")
    INVALID_GENERATION_FILTER(BAD_REQUEST, "NEWS_400_4", "기수(generation) 필터 값이 올바르지 않습니다."),

    @ExplainError("뉴스/소식 제목(title)이 null/blank인 경우 발생합니다.")
    TITLE_REQUIRED(BAD_REQUEST, "NEWS_400_5", "제목은 필수입니다."),

    @ExplainError("뉴스/소식 본문(content)이 null/blank인 경우 발생합니다.")
    CONTENT_REQUIRED(BAD_REQUEST, "NEWS_400_6", "내용은 필수입니다."),

    @ExplainError("제목 길이가 허용 최대치를 초과한 경우 발생합니다. (200자)")
    TITLE_TOO_LONG(BAD_REQUEST, "NEWS_400_7", "제목 길이가 허용 범위를 초과했습니다."),

//    본문 최대 길이 지정..?(공지도)
//    @ExplainError("본문 길이가 허용 최대치를 초과한 경우 발생합니다.")
//    CONTENT_TOO_LONG(BAD_REQUEST, "NEWS_400_8", "내용 길이가 허용 범위를 초과했습니다."),

    @ExplainError("작성자 식별자(authorId)가 누락되었거나 유효하지 않은 경우 발생합니다.")
    AUTHOR_ID_INVALID(BAD_REQUEST, "NEWS_400_8", "작성자 정보가 올바르지 않습니다."),

    @ExplainError("thumbnailUrl이 존재하지만 URL 형식이 올바르지 않은 경우 발생합니다.")
    INVALID_THUMBNAIL_URL(BAD_REQUEST, "NEWS_400_9", "썸네일 URL 형식이 올바르지 않습니다."),

    @ExplainError("첨부 파일 리스트는 존재하지만 내부 요소가 null이거나 필수 필드가 비어있는 경우 발생합니다.")
    INVALID_FILE_PAYLOAD(BAD_REQUEST, "NEWS_400_10", "첨부 파일 정보가 올바르지 않습니다."),

    @ExplainError("첨부 파일 URL이 비어있거나 URL 형식이 올바르지 않은 경우 발생합니다.")
    INVALID_FILE_URL(BAD_REQUEST, "NEWS_400_11", "첨부 파일 URL 형식이 올바르지 않습니다."),

    @ExplainError("첨부 파일 타입(enum)이 유효하지 않거나 서버가 지원하지 않는 타입인 경우 발생합니다.")
    INVALID_FILE_TYPE(BAD_REQUEST, "NEWS_400_12", "첨부 파일 타입이 올바르지 않습니다."),

    @ExplainError("동일 조건에서 중복 생성 등 도메인 무결성 정책에 위배되는 경우 발생합니다.")
    NEWS_CONFLICT(CONFLICT, "NEWS_409_1", "뉴스/소식 생성 요청이 충돌했습니다.");


    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

    @Override
    public String getExplainError() {
        return this.reason;
    }
}
