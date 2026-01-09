package com.example.backend.auth.repository;

import com.example.backend.auth.entity.PasswordReset;
import com.example.backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
	Optional<PasswordReset> findByToken(String token);
	
	Optional<PasswordReset> findByUser(User user);
	
	@Query("SELECT pr FROM PasswordReset pr WHERE pr.user.email = :email")
	Optional<PasswordReset> findByUserEmail(@Param("email") String email);
	
	boolean existsByUserAndUsedAtIsNull(User user);
}

