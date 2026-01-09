package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogoutRequest {

	@Schema(description = "Firebase ID 토큰", example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...")
	@NotBlank(message = "idToken은 필수입니다.")
	private String idToken;

	@Schema(description = "학교 이메일(@seoultech.ac.kr)", example = "example@seoultech.ac.kr")
	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
}
