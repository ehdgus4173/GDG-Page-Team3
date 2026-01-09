package com.example.backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Auth 도메인 관련 Bean 설정
 * CORS 설정은 SecurityConfig에서 관리합니다.
 */
@Configuration
public class CorsConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
