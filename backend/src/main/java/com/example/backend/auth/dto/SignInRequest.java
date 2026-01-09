package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignInRequest {

    @Schema(description = "학교 이메일(@seoultech.ac.kr)", example = "example@seoultech.ac.kr")
    private String email;

    @Schema(description = "비밀번호", example = "1234qwer")
    private String password;
}

