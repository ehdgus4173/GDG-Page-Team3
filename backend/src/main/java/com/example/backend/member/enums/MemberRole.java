package com.example.backend.member.enums;

public enum MemberRole {
    LEAD,
    CORE,
    MEMBER;

    public static MemberRole fromLocalized(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("role is required");
        }

        String normalized = value.trim().toLowerCase();
        return switch (normalized) {
            case "리드", "lead" -> LEAD;
            case "코어", "core" -> CORE;
            case "멤버", "member" -> MEMBER;
            default -> MemberRole.valueOf(value.trim().toUpperCase());
        };
    }
}
