package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {

    @Schema(description = "응답 메시지", example = "이메일 인증 링크가 발송되었습니다.")
    private String message;
}
