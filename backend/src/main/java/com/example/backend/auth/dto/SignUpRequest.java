package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "학년", example = "5")
    private Integer grade;

    @Schema(description = "직책", example = "Core")
    private String position;

    @Schema(description = "파트", example = "BE")
    private String part;

    @Schema(description = "학번", example = "12345678")
    private String stdId;

    @Schema(description = "학과", example = "ITM")
    private String department;

    @Schema(description = "학교 이메일(@seoultech.ac.kr)", example = "example@seoultech.ac.kr")
    private String email;

    @Schema(description = "비밀번호", example = "1234qwer")
    private String password;
}

