package com.example.backend.member.repository;

import com.example.backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
	boolean existsByEmailIgnoreCase(String email);
	java.util.Optional<User> findByEmailIgnoreCase(String email);
}

