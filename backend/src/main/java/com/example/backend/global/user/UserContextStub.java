package com.example.backend.global.user;

import org.springframework.stereotype.Component;

@Component
public class UserContextStub implements UserContext {

    // TODO : auth DTO 확정되면 교체

    @Override
    public Long currentUserId() {
        return 2L; // TODO: 인증 붙이면 교체(이부분이진짜authorId)
    }

    @Override
    public int currentGeneration() {
        return 0; // TODO: 인증 붙이면 교체
    }
}
