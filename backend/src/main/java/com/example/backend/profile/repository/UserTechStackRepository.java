package com.example.backend.profile.repository;


import com.example.backend.member.entity.User;
import com.example.backend.member.entity.UserTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTechStackRepository extends JpaRepository<UserTechStack, Long> {

    // userId로 여러 개 조회
    List<UserTechStack> findByUserId(Long userId);

    // userId로 삭제
    void deleteByUserId(Long userId);

    // 또는 User 엔티티로 조회/삭제
    List<UserTechStack> findByUser(User user);

    void deleteByUser(User user);
}

