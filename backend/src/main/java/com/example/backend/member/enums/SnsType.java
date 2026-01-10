package com.example.backend.member.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SnsType {
    GITHUB,
    BLOG,
    LINKEDIN,
    INSTAGRAM,
    BEHANCE,
    ETC;

    /**
     * 프런트에서 들어오는 값들이 대소문자/서비스명 상이할 수 있어 관용적으로 매핑.
     * 예: "Github", "VELoG", "Notion", "Portfolio" 등도 처리.
     */
    @JsonCreator
    public static SnsType from(String value) {
        if (value == null || value.isBlank()) {
            return ETC;
        }
        String v = value.trim().toUpperCase();
        return switch (v) {
            case "GITHUB", "GIT" -> GITHUB;
            case "BLOG", "VELO G", "VELOG", "TISTORY", "BRUNCH" -> BLOG;
            case "LINKEDIN" -> LINKEDIN;
            case "INSTAGRAM", "IG" -> INSTAGRAM;
            case "BEHANCE" -> BEHANCE;
            case "NOTION", "PORTFOLIO" -> ETC;
            default -> ETC;
        };
    }
}
