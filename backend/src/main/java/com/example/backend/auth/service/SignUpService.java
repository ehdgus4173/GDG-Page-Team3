package com.example.backend.auth.service;

import com.example.backend.auth.config.AuthProperties;
import com.example.backend.auth.dto.SignUpConfirmRequest;
import com.example.backend.auth.exception.SignUpErrorCode;
import com.example.backend.auth.exception.SignUpException;
import com.example.backend.member.entity.User;
import com.example.backend.member.enums.MemberRole;
import com.example.backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.auth.FirebaseToken;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final FirebaseService firebaseService;
	private final AuthProperties authProperties;

	@Transactional
	public String confirmSignUp(SignUpConfirmRequest request) {
		// 1. 이메일 도메인/형식 검증
		validateEmailDomain(request.getEmail());
		validateEmailFormat(request.getEmail());

		// 2. 중복 이메일 확인 (이미 DB에 존재하는지)
		checkDuplicateEmail(request.getEmail());

		// 3. Firebase 토큰 검증 및 이메일 일치 확인
		FirebaseToken token = firebaseService.verifyIdToken(request.getIdToken());
		if (token.getEmail() == null || !token.getEmail().equals(request.getEmail())) {
			log.warn("Email mismatch: request={}, token={}", request.getEmail(), token.getEmail());
			throw new SignUpException(SignUpErrorCode.FIREBASE_TOKEN_VERIFICATION_FAILED);
		}

		// 4. Firebase 이메일 인증 여부 확인
		if (!token.isEmailVerified()) {
			log.warn("Email not verified yet: {}", token.getEmail());
			throw new SignUpException(SignUpErrorCode.EMAIL_NOT_VERIFIED);
		}

		// 5. MemberRole 변환
		MemberRole role = convertToMemberRole(request.getPosition());

		// 6. DB에 사용자 저장 (비밀번호는 Firebase에서 관리하므로 placeholder)
		String placeholderPassword = passwordEncoder.encode("firebase-auth");

		User user = User.builder()
			.name(request.getName())
			.generation(request.getGrade())
			.role(role)
			.part(request.getPart())
			.studentId(request.getStdId())
			.department(request.getDepartment())
			.email(request.getEmail())
			.passwordHash(placeholderPassword)
			.emailVerified(true)
			.build();

		userRepository.save(user);
		log.info("User created after email verification: {}", request.getEmail());

		return "이메일 인증이 완료되었습니다.";
	}

	/**
	 * 이메일 도메인 검증 (@seoultech.ac.kr만 허용)
	 */
	private void validateEmailDomain(String email) {
		boolean isValidDomain = authProperties.getAllowedEmailDomains().stream()
			.anyMatch(domain -> email.endsWith("@" + domain));

		if (!isValidDomain) {
			log.warn("Invalid email domain: {}", email);
			throw new SignUpException(SignUpErrorCode.EMAIL_DOMAIN_INVALID);
		}
	}

	/**
	 * 이메일 형식 검증
	 */
	private void validateEmailFormat(String email) {
		if (email == null || email.isBlank() || !email.contains("@")) {
			log.warn("Invalid email format: {}", email);
			throw new SignUpException(SignUpErrorCode.INVALID_EMAIL_FORMAT);
		}
	}

	/**
	 * 중복 이메일 확인 (이미 DB에 존재하는 User인지)
	 */
	private void checkDuplicateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			log.warn("Email already exists: {}", email);
			throw new SignUpException(SignUpErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	/**
	 * 비밀번호 검증
	 */
	private void validatePassword(String password) {
		if (password == null || password.length() < 8) {
			log.warn("Password does not meet requirements");
			throw new SignUpException(SignUpErrorCode.INVALID_PASSWORD_FORMAT);
		}
	}

	/**
	 * position 문자열을 MemberRole enum으로 변환
	 */
	private MemberRole convertToMemberRole(String position) {
		if (position == null || position.isBlank()) {
			throw new SignUpException(SignUpErrorCode.REQUIRED_FIELD_MISSING);
		}

		try {
			return MemberRole.valueOf(position.toUpperCase());
		} catch (IllegalArgumentException e) {
			log.warn("Invalid position: {}", position);
			throw new SignUpException(SignUpErrorCode.REQUIRED_FIELD_MISSING);
		}
	}
}

