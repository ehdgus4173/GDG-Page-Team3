package com.example.backend.global.document;

import com.example.backend.global.annotation.ApiErrorCodeExample;
import com.example.backend.global.annotation.DevelopOnlyApi;
import com.example.backend.global.exception.GlobalErrorCode;
import com.example.backend.main.exception.MainErrorCode;
import com.example.backend.news.exception.NewsErrorCode;
import com.example.backend.notice.exception.NoticeErrorCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/example")
@RequiredArgsConstructor
@Tag(name = "Exception Document", description = "예제 에러코드 문서화")
public class ExampleController {
	@GetMapping("/global")
	@DevelopOnlyApi
	@ApiErrorCodeExample(GlobalErrorCode.class)
	@Operation(summary = "글로벌 (aop, 서버 내부 오류등)  관련 에러 코드 나열")
	public void example() {
	}

    @GetMapping("/main")
    @DevelopOnlyApi
    @ApiErrorCodeExample(MainErrorCode.class)
    @Operation(summary = "메인페이지 관련 에러 코드 나열")
    public void example2() {
    }

    @GetMapping("/news")
    @DevelopOnlyApi
    @ApiErrorCodeExample(NewsErrorCode.class)
    @Operation(summary = "뉴스/소식 관련 에러 코드 나열")
    public void example3() {
    }

    @GetMapping("/notice")
    @DevelopOnlyApi
    @ApiErrorCodeExample(NoticeErrorCode.class)
    @Operation(summary = "공지 관련 에러 코드 나열")
    public void example4() {
    }

}
