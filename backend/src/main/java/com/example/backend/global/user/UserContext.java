package com.example.backend.global.user;

public interface UserContext {
    Long currentUserId();

    int currentGeneration();
}
