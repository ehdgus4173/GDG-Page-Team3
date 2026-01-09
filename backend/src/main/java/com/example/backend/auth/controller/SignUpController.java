package com.example.backend.auth.controller;

import com.example.backend.auth.dto.SignUpConfirmRequest;
import com.example.backend.auth.docs.SignUpExceptionDocs;
import com.example.backend.auth.service.SignUpService;
import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignUpController {

	private final SignUpService signUpService;

	@PostMapping("/signup/confirm")
	@Operation(summary = "이메일 인증 완료", description = "Firebase ID 토큰을 검증하고 이메일 인증이 완료된 사용자를 DB에 저장합니다.")
	@ApiErrorExceptionsExample(SignUpExceptionDocs.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> confirmSignUp(
		@Valid @RequestBody SignUpConfirmRequest request
	) {
		String message = signUpService.confirmSignUp(request);
		Map<String, String> data = Map.of("message", message);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(data));
	}
}

