package com.example.backend.member.entity;

import com.example.backend.member.enums.MemberRole;
import com.example.backend.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
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

    @Column(nullable = false)
    @Builder.Default
    private boolean emailVerified = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /* ===============================
       Domain Methods
       =============================== */

    public void verifyEmail() {
        this.emailVerified = true;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePart(String part) {
        this.part = part;
    }

    public void updateDepartment(String department) {
        this.department = department;
    }

    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void updateGeneration(int generation) {
        this.generation = generation;
    }

    public void updateRole(MemberRole role) {
        this.role = role;
    }
}
