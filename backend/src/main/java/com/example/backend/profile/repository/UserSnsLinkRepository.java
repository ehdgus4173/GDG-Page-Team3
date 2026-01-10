package com.example.backend.profile.repository;


import com.example.backend.member.entity.User;
import com.example.backend.member.entity.UserSnsLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSnsLinkRepository extends JpaRepository<UserSnsLink, Long> {

    // userId로 여러 개 조회 (List 반환)
    List<UserSnsLink> findByUserId(Long userId);

    // userId로 삭제
    void deleteByUserId(Long userId);

    // 또는 User 엔티티로 조회/삭제하는 방식
    List<UserSnsLink> findByUser(User user);
    void deleteByUser(User user);
}
