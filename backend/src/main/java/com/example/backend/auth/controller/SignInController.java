package com.example.backend.auth.controller;

import com.example.backend.auth.docs.SignInExceptionDocs;
import com.example.backend.auth.dto.SignInRequest;
import com.example.backend.auth.service.SignInService;
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
public class SignInController {

	private final SignInService signInService;

	@PostMapping("/signin")
	@Operation(summary = "로그인", description = "Firebase ID 토큰을 검증하고 이메일 인증 여부를 확인합니다.")
	@ApiErrorExceptionsExample(SignInExceptionDocs.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> signIn(
		@Valid @RequestBody SignInRequest request
	) {
		String message = signInService.signIn(request);
		Map<String, String> data = Map.of("message", message);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(data));
	}
}
