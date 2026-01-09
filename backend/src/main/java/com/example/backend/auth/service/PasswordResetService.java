package com.example.backend.auth.service;

import com.example.backend.auth.config.AuthProperties;
import com.example.backend.auth.dto.PasswordResetRequest;
import com.example.backend.auth.exception.PasswordResetErrorCode;
import com.example.backend.auth.exception.PasswordResetException;
import com.example.backend.member.entity.User;
import com.example.backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PasswordResetService {

	private final AuthProperties authProperties;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 비밀번호 변경 처리 (Firebase에서 oobCode 검증을 완료했다는 전제)
	 */
	@Transactional
	public String resetPassword(PasswordResetRequest request) {
		final String email = normalizeEmail(request.getEmail());
		validateEmailDomain(email);
		validatePassword(request.getNewPassword());

		User user = userRepository.findByEmailIgnoreCase(email)
			.orElseThrow(() -> new PasswordResetException(PasswordResetErrorCode.USER_NOT_FOUND));

		if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
			throw new PasswordResetException(PasswordResetErrorCode.SAME_AS_OLD_PASSWORD);
		}

		user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
		log.info("Password updated for user id={} email={}", user.getId(), email);
		return "비밀번호가 변경되었습니다.";
	}

	private void validateEmailDomain(String email) {
		boolean valid = authProperties.getAllowedEmailDomains().stream()
			.anyMatch(domain -> email.endsWith("@" + domain));
		if (!valid) {
			throw new PasswordResetException(PasswordResetErrorCode.INVALID_EMAIL_DOMAIN);
		}
	}

	private void validatePassword(String password) {
		if (password == null || password.length() < 8) {
			throw new PasswordResetException(PasswordResetErrorCode.PASSWORD_POLICY_VIOLATION);
		}
		boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-_=+\\[{\\]}|;:'\",<.>/?].*");
		if (!hasSpecial) {
			throw new PasswordResetException(PasswordResetErrorCode.PASSWORD_POLICY_VIOLATION);
		}
	}

	private String normalizeEmail(String email) {
		if (email == null) {
			throw new PasswordResetException(PasswordResetErrorCode.INVALID_EMAIL_DOMAIN);
		}
		return email.trim().toLowerCase();
	}
}
