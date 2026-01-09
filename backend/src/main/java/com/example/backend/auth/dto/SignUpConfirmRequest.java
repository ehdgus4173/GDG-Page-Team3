package com.example.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpConfirmRequest {

	@Schema(description = "Firebase ID 토큰 (이메일 인증 완료 후 발급)", example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...")
	@NotBlank(message = "idToken은 필수입니다.")
	private String idToken;

	@Schema(description = "학교 이메일(@seoultech.ac.kr)", example = "example@seoultech.ac.kr")
	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;

	@Schema(description = "이름", example = "홍길동")
	@NotBlank(message = "이름은 필수입니다.")
	@Size(max = 50, message = "이름은 50자 이하여야 합니다.")
	private String name;

	@Schema(description = "학년", example = "5")
	@NotNull(message = "학년은 필수입니다.")
	private Integer grade;

	@Schema(description = "직책", example = "Core")
	@NotBlank(message = "직책은 필수입니다.")
	private String position;

	@Schema(description = "파트", example = "BE")
	@NotBlank(message = "파트는 필수입니다.")
	@Size(max = 50, message = "파트는 50자 이하여야 합니다.")
	private String part;

	@Schema(description = "학번", example = "12345678")
	@NotBlank(message = "학번은 필수입니다.")
	@Size(max = 20, message = "학번은 20자 이하여야 합니다.")
	private String stdId;

	@Schema(description = "학과", example = "ITM")
	@NotBlank(message = "학과는 필수입니다.")
	@Size(max = 100, message = "학과는 100자 이하여야 합니다.")
	private String department;
}

