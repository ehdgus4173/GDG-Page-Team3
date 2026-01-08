package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PasswordResetRequest {

    @Schema(description = "학교 이메일(@seoultech.ac.kr)", example = "user@seoultech.ac.kr")
    private String email;

    @Schema(description = "새 비밀번호", example = "newPassword123!")
    private String newPassword;
}

