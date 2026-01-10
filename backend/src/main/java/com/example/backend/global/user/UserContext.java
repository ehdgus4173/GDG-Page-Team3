package com.example.backend.global.user;

public interface UserContext {
    Long currentUserId();

    int currentGeneration();

    com.example.backend.member.enums.MemberRole currentRole();
}
