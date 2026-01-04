package com.example.backend.member.entity;

import com.example.backend.common.BaseEntity;
import com.example.backend.member.enums.MemberRole; // 프로젝트의 실제 Role Enum 경로로 확인 필요
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity { // 1. BaseEntity 상속 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole role;

    @Column(length = 50, nullable = false)
    private String part;

    @Column(name = "student_id", length = 20, nullable = false)
    private String studentId;

    @Column(length = 100, nullable = false)
    private String department;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private Boolean isEmailVerified = false;

    /* ===============================
       Domain Methods (선택)
       =============================== */

    public void verifyEmail() {
        this.isEmailVerified = true;
    }
}