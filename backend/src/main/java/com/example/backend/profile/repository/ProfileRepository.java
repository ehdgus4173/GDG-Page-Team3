package com.example.backend.profile.repository;

import com.example.backend.profile.entity.Profile;
import com.example.backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(User user);
}
