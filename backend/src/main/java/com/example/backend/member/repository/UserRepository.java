package com.example.backend.member.repository;

import com.example.backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByGenerationAndPart(Integer generation, String part);

    List<User> findByGeneration(Integer generation);

    List<User> findByPart(String part);
}
