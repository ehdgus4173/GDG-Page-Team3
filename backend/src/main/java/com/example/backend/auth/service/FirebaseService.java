package com.example.backend.auth.service;

import com.example.backend.auth.exception.PasswordResetErrorCode;
import com.example.backend.auth.exception.PasswordResetException;
import com.example.backend.auth.exception.SignUpErrorCode;
import com.example.backend.auth.exception.SignUpException;
import com.example.backend.auth.exception.SignInErrorCode;
import com.example.backend.auth.exception.SignInException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService {

	private final FirebaseAuth firebaseAuth;

	/**
	 * Firebase 토큰 검증
	 * @param idToken Firebase ID 토큰
	 * @return 검증된 FirebaseToken
	 * @throws SignUpException 토큰 검증 실패 시
	 */
	public FirebaseToken verifyIdToken(String idToken) {
		try {
			FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
			log.info("Firebase token verified successfully for uid: {}", decodedToken.getUid());
			return decodedToken;
		} catch (FirebaseAuthException e) {
			log.error("Firebase token verification failed: {}", e.getMessage());
			throw new SignUpException(SignUpErrorCode.FIREBASE_TOKEN_VERIFICATION_FAILED);
		}
	}

	/**
	 * Firebase에 사용자 생성 (비밀번호 포함)
	 * @param email 사용자 이메일
	 * @param password 사용자 비밀번호
	 * @return 생성된 사용자 UID
	 * @throws SignUpException 사용자 생성 실패 시
	 */
	public String createFirebaseUser(String email, String password) {
		try {
			UserRecord.CreateRequest request = new UserRecord.CreateRequest()
				.setEmail(email)
				.setPassword(password)
				.setEmailVerified(false)
				.setDisabled(false);

			UserRecord userRecord = firebaseAuth.createUser(request);
			log.info("Firebase user created: {} (uid: {})", email, userRecord.getUid());
			return userRecord.getUid();
		} catch (FirebaseAuthException e) {
			if (e.getErrorCode().equals("email-already-exists")) {
				// 이미 존재하는 경우 조회만 수행
				log.info("Firebase user already exists: {}", email);
				try {
					UserRecord existingUser = firebaseAuth.getUserByEmail(email);
					return existingUser.getUid();
				} catch (FirebaseAuthException ex) {
					log.error("Failed to get existing Firebase user: {}", ex.getMessage());
					throw new SignUpException(SignUpErrorCode.FIREBASE_AUTH_SERVICE_ERROR);
				}
			} else {
				if ("INVALID_PASSWORD".equals(e.getErrorCode()) || "INVALID_ARGUMENT".equals(e.getErrorCode())) {
					// Firebase 비밀번호 정책 위반 (6자 미만 등)
					log.warn("Firebase password policy violation for {}: {}", email, e.getMessage());
					throw new SignUpException(SignUpErrorCode.INVALID_PASSWORD_FORMAT);
				}
				log.error("Failed to create Firebase user for {}: {} (code={})", email, e.getMessage(), e.getErrorCode());
				throw new SignUpException(SignUpErrorCode.FIREBASE_AUTH_SERVICE_ERROR);
			}
		}
	}

	/**
	 * 이메일 인증 링크 생성 및 발송
	 * Firebase Admin SDK를 사용하여 이메일 인증 링크를 생성합니다.
	 * 실제 이메일 발송은 Firebase가 자동으로 처리합니다 (Firebase Console에서 이메일 템플릿 설정 필요)
	 * 
	 * @param email 사용자 이메일
	 * @param continueUrl 인증 완료 후 리다이렉트 URL
	 * @return 인증 링크 URL (실제 이메일은 Firebase가 자동 발송)
	 * @throws SignUpException 링크 생성 실패 시
	 */
	public String sendEmailVerificationLink(String email, String continueUrl) {
		try {
			// Action Code Settings 설정
			ActionCodeSettings actionCodeSettings = ActionCodeSettings.builder()
				.setUrl(continueUrl) // 인증 완료 후 리다이렉트 URL
				.setHandleCodeInApp(false) // 앱 내에서 처리하지 않음 (웹에서 처리)
				.build();

			// Firebase에서 이메일 인증 링크 생성
			// 참고: Firebase는 이 링크를 이메일로 자동 발송하지 않습니다.
			// 이 링크를 직접 이메일로 발송하거나, Firebase Console에서 이메일 템플릿을 설정해야 합니다.
			String link = firebaseAuth.generateEmailVerificationLink(email, actionCodeSettings);
			log.info("Email verification link generated for: {}", email);
			log.debug("Verification link: {}", link);
			
			// 생성된 링크를 반환 (실제 이메일 발송은 별도로 처리 필요)
			return link;
		} catch (FirebaseAuthException e) {
			log.error("Failed to generate email verification link for {}: {}", email, e.getMessage());
			throw new SignUpException(SignUpErrorCode.FIREBASE_TOKEN_VERIFICATION_FAILED);
		}
	}

	/**
	 * 비밀번호 재설정 이메일 발송
	 * @param email 사용자 이메일
	 * @param continueUrl 비밀번호 재설정 완료 후 리다이렉트 URL
	 * @return 비밀번호 재설정 링크 URL
	 * @throws PasswordResetException 링크 생성 실패 시
	 */
	public String sendPasswordResetEmail(String email, String continueUrl) {
		try {
			ActionCodeSettings actionCodeSettings = ActionCodeSettings.builder()
				.setUrl(continueUrl)
				.setHandleCodeInApp(false)
				.build();

			String link = firebaseAuth.generatePasswordResetLink(email, actionCodeSettings);
			log.info("Password reset link generated for: {}", email);
			return link;
		} catch (FirebaseAuthException e) {
			log.error("Failed to generate password reset link for {}: {}", email, e.getMessage());
			throw new PasswordResetException(PasswordResetErrorCode.FIREBASE_PASSWORD_RESET_EMAIL_FAILED);
		}
	}

	/**
	 * 이메일로 사용자 조회
	 * @param email 사용자 이메일
	 * @return UserRecord 또는 null
	 */
	public UserRecord getUserByEmail(String email) {
		try {
			return firebaseAuth.getUserByEmail(email);
		} catch (FirebaseAuthException e) {
			log.debug("User not found with email: {}", email);
			return null;
		}
	}

	/**
	 * 사용자 이메일 인증 상태 업데이트
	 * @param email 사용자 이메일
	 * @param emailVerified 인증 상태
	 */
	public void updateEmailVerifiedStatus(String email, boolean emailVerified) {
		try {
			UserRecord userRecord = firebaseAuth.getUserByEmail(email);
			UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
				.setEmailVerified(emailVerified);
			firebaseAuth.updateUser(request);
			log.info("Email verified status updated for {}: {}", email, emailVerified);
		} catch (FirebaseAuthException e) {
			log.error("Failed to update email verified status for {}: {}", email, e.getMessage());
			throw new SignUpException(SignUpErrorCode.FIREBASE_TOKEN_VERIFICATION_FAILED);
		}
	}

	/**
	 * 사용자 비밀번호 변경
	 * @param email 사용자 이메일
	 * @param newPasswordHash 새로운 비밀번호 해시 (BCrypt 해시값)
	 */
	public void updateUserPassword(String email, String newPasswordHash) {
		try {
			UserRecord userRecord = firebaseAuth.getUserByEmail(email);
			// Firebase는 자체 해시를 사용하므로 직접 비밀번호를 설정할 수 없습니다.
			// 이 메서드는 필요시 Firebase 사용자 정보를 업데이트하는 용도로 사용합니다.
			log.info("Password update requested for: {}", email);
		} catch (FirebaseAuthException e) {
			log.error("Failed to update password for {}: {}", email, e.getMessage());
			throw new PasswordResetException(PasswordResetErrorCode.FIREBASE_PASSWORD_RESET_EMAIL_FAILED);
		}
	}

	/**
	 * Refresh Token 폐기 (로그아웃 처리)
	 * @param uid Firebase UID
	 */
	public void revokeRefreshTokens(String uid) {
		try {
			firebaseAuth.revokeRefreshTokens(uid);
			log.info("Refresh tokens revoked for uid={}", uid);
		} catch (FirebaseAuthException e) {
			log.error("Failed to revoke refresh tokens for uid={}: {}", uid, e.getMessage());
			throw new SignInException(SignInErrorCode.LOGOUT_FAILED);
		}
	}
}


