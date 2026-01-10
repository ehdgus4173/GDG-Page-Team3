package com.example.backend.auth.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    private final ResourceLoader resourceLoader;

    @Value("${firebase.credentials.resource:}")
    private String credentialsLocation;

    @Value("${firebase.project-id}")
    private String projectId;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        FirebaseOptions options;
        String credentialsJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            // ENV 변수에서 JSON 내용 직접 로드 (Render 등 배포 환경용)
            try (InputStream serviceAccount = new java.io.ByteArrayInputStream(
                    credentialsJson.getBytes(java.nio.charset.StandardCharsets.UTF_8))) {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId(projectId)
                        .build();
            }
        } else {
            // 로컬 파일/리소스에서 로드 (개발 환경용)
            Resource resource = resourceLoader.getResource(credentialsLocation);
            try (InputStream serviceAccount = resource.getInputStream()) {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId(projectId)
                        .build();
            }
        }

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
