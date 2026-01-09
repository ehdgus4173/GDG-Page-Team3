package com.example.backend.auth.service;

import com.example.backend.auth.dto.LogoutRequest;
import com.example.backend.auth.exception.SignInErrorCode;
import com.example.backend.auth.exception.SignInException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

	private final FirebaseService firebaseService;

	public String logout(LogoutRequest request) {
		FirebaseToken token = firebaseService.verifyIdToken(request.getIdToken());
		validateTokenEmail(token, request.getEmail());

		try {
			firebaseService.revokeRefreshTokens(token.getUid());
			log.info("Logout succeeded for {}", request.getEmail());
			return "로그아웃이 완료되었습니다.";
		} catch (Exception e) {
			log.error("Logout failed for {}: {}", request.getEmail(), e.getMessage());
			throw new SignInException(SignInErrorCode.LOGOUT_FAILED);
		}
	}

	private void validateTokenEmail(FirebaseToken token, String email) {
		if (token.getEmail() == null || !token.getEmail().equalsIgnoreCase(email)) {
			log.warn("Logout token/email mismatch. tokenEmail={}, requestEmail={}", token.getEmail(), email);
			throw new SignInException(SignInErrorCode.INVALID_TOKEN);
		}
	}
}
