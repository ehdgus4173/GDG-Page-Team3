package com.example.backend.auth.controller;

import com.example.backend.auth.docs.PasswordResetExceptionDocs;
import com.example.backend.auth.dto.PasswordResetRequest;
import com.example.backend.auth.service.PasswordResetService;
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
@RequestMapping("/api/auth/password")
@RequiredArgsConstructor
public class PasswordResetController {

	private final PasswordResetService passwordResetService;

	@PostMapping("/reset")
	@Operation(summary = "비밀번호 재설정", description = "Firebase 이메일 링크 인증 후 새 비밀번호로 변경합니다.")
	@ApiErrorExceptionsExample(PasswordResetExceptionDocs.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> resetPassword(
		@Valid @RequestBody PasswordResetRequest request
	) {
		String message = passwordResetService.resetPassword(request);
		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.success(Map.of("message", message)));
	}
}
