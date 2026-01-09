# Firebase 이메일 인증 설정 가이드

## 현재 구현 상태

✅ **완료된 것:**
- Firebase Admin SDK 초기화 완료
- 이메일 인증 링크 생성 기능 구현 (`FirebaseService.sendEmailVerificationLink()`)
- 이메일 인증 토큰 검증 기능 구현 (`FirebaseService.verifyIdToken()`)
- 이메일 인증 상태 업데이트 기능 구현 (`FirebaseService.updateEmailVerifiedStatus()`)

⚠️ **추가 설정 필요:**

## 1. Firebase Console 설정

### 1.1 Authentication 활성화
1. [Firebase Console](https://console.firebase.google.com/) 접속
2. 프로젝트 선택 (`gdg-team3`)
3. **Authentication** 메뉴로 이동
4. **Get Started** 클릭 (처음 사용하는 경우)

### 1.2 이메일/비밀번호 인증 활성화
1. **Sign-in method** 탭 선택
2. **Email/Password** 항목 클릭
3. **Enable** 토글 활성화
4. **Email link (passwordless sign-in)** 옵션은 선택하지 않음 (일반 이메일/비밀번호 방식 사용)
5. **Save** 클릭

### 1.3 승인된 도메인 설정
1. Authentication > **Settings** 탭으로 이동
2. **Authorized domains** 섹션에서
3. **Add domain** 클릭
4. 다음 도메인 추가:
   - `localhost` (개발 환경)
   - `seoultech.ac.kr` (학교 이메일 도메인)
   - 프로덕션 도메인 (배포 시)

### 1.4 이메일 템플릿 커스터마이징 (선택사항)
1. Authentication > **Templates** 탭으로 이동
2. **Email address verification** 템플릿 선택
3. 이메일 제목과 내용 커스터마이징
4. **Save** 클릭

**중요:** Firebase는 `generateEmailVerificationLink()`로 생성된 링크를 자동으로 이메일로 발송하지 않습니다.
따라서 다음 중 하나의 방법을 선택해야 합니다:

## 2. 이메일 발송 방법 선택

### 방법 1: JavaMailSender 사용 (권장 - 완전한 제어)

회원가입 시 생성된 링크를 JavaMailSender로 직접 발송:

```java
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final FirebaseService firebaseService;
    
    public void sendVerificationEmail(String email, String continueUrl) {
        String verificationLink = firebaseService.sendEmailVerificationLink(email, continueUrl);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증을 완료해주세요");
        message.setText("다음 링크를 클릭하여 이메일 인증을 완료하세요:\n\n" + verificationLink);
        
        mailSender.send(message);
    }
}
```

### 방법 2: Firebase Auth 이메일 템플릿 사용

Firebase Console에서 이메일 템플릿을 설정하고, 
Firebase 클라이언트 SDK에서 `sendEmailVerification()` 메서드를 사용합니다.
(서버에서 직접 호출 불가 - 클라이언트에서 처리)

### 방법 3: Firebase Extension 사용

Firebase Extensions에서 "Trigger Email" 확장을 설치하면
Firebase Auth 이벤트에 따라 자동으로 이메일을 발송할 수 있습니다.

## 3. 현재 코드 동작 방식

### 3.1 회원가입 플로우
1. 사용자가 회원가입 요청 (`/api/auth/signup`)
2. 서버에서 Firebase 이메일 인증 링크 생성 (`FirebaseService.sendEmailVerificationLink()`)
3. **생성된 링크를 이메일로 발송** (JavaMailSender 등으로 구현 필요)
4. 사용자가 이메일의 링크 클릭
5. 클라이언트에서 Firebase SDK로 링크 검증
6. 서버에 토큰 전송하여 인증 완료 (`/api/auth/email/verify`)

### 3.2 이메일 인증 완료 플로우
1. 클라이언트에서 Firebase 토큰 받음
2. 서버에 토큰 전송 (`POST /api/auth/email/verify`)
3. 서버에서 토큰 검증 (`FirebaseService.verifyIdToken()`)
4. 사용자 정보 업데이트 (`email_verified = true`)
5. Firebase에서도 인증 상태 업데이트 (`FirebaseService.updateEmailVerifiedStatus()`)

## 4. 추가 구현 필요 사항

### 4.1 이메일 발송 서비스 (JavaMailSender)
```yaml
# application.yml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

```gradle
// build.gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-mail'
}
```

### 4.2 이메일 템플릿 (Thymeleaf 또는 FreeMarker)
HTML 형식의 이메일 템플릿 작성

## 5. 테스트 방법

### 5.1 로컬 테스트
1. 이메일 인증 링크 생성 확인
2. 링크가 올바른 형식인지 확인
3. 링크를 브라우저에서 열어 동작 확인

### 5.2 Firebase Console에서 확인
1. Authentication > Users 탭에서 사용자 목록 확인
2. 이메일 인증 상태 확인

## 참고 사항

⚠️ **중요:**
- `generateEmailVerificationLink()`는 링크만 생성하고 이메일을 자동 발송하지 않습니다.
- 실제 이메일 발송을 위해서는 JavaMailSender나 다른 이메일 서비스를 사용해야 합니다.
- Firebase Auth의 `sendEmailVerification()`은 클라이언트 SDK에서만 사용 가능합니다.

