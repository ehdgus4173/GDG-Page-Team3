package com.example.backend.auth.controller;

import com.example.backend.auth.docs.SignInExceptionDocs;
import com.example.backend.auth.dto.LogoutRequest;
import com.example.backend.auth.service.LogoutService;
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
public class LogoutController {

	private final LogoutService logoutService;

	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "Firebase ID 토큰을 검증하고 Refresh Token을 폐기합니다.")
	@ApiErrorExceptionsExample(SignInExceptionDocs.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> logout(
		@Valid @RequestBody LogoutRequest request
	) {
		String message = logoutService.logout(request);
		Map<String, String> data = Map.of("message", message);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(data));
	}
}
