package com.example.backend.profile.entity;

import com.example.backend.member.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String department;

    private String studentId;

    private String profileImageUrl;

    public Profile(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateDepartment(String department) {
        this.department = department;
    }

    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}