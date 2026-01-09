package com.example.backend.auth.service;

import com.example.backend.auth.dto.SignInRequest;
import com.example.backend.auth.exception.SignInErrorCode;
import com.example.backend.auth.exception.SignInException;
import com.example.backend.member.entity.User;
import com.example.backend.member.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignInService {

	private final FirebaseService firebaseService;
	private final UserRepository userRepository;

	public String signIn(SignInRequest request) {
		final String email = request.getEmail().trim().toLowerCase();

		// 1) Firebase ID 토큰 검증 및 이메일 매칭
		FirebaseToken token = firebaseService.verifyIdToken(request.getIdToken());
		validateTokenEmail(token, email);

		// 2) 이메일 인증 여부 확인
		if (!token.isEmailVerified()) {
			log.warn("Email not verified for sign-in: {}", email);
			throw new SignInException(SignInErrorCode.EMAIL_NOT_VERIFIED);
		}

		// 3) DB 사용자 조회
		User user = userRepository.findByEmailIgnoreCase(email)
			.orElseThrow(() -> {
				log.warn("User not found for sign-in: {}", email);
				return new SignInException(SignInErrorCode.EMAIL_NOT_FOUND);
			});

		if (!user.isEmailVerified()) {
			log.warn("DB email not verified for sign-in: {}", email);
			throw new SignInException(SignInErrorCode.EMAIL_NOT_VERIFIED);
		}

		log.info("Sign-in succeeded for {}", email);
		return "로그인에 성공했습니다.";
	}

	private void validateTokenEmail(FirebaseToken token, String email) {
		if (token.getEmail() == null || !token.getEmail().equalsIgnoreCase(email)) {
			log.warn("Sign-in token/email mismatch. tokenEmail={}, requestEmail={}", token.getEmail(), email);
			throw new SignInException(SignInErrorCode.INVALID_TOKEN);
		}
	}
}
