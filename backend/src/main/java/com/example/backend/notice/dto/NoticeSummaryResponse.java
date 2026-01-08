package com.example.backend.notice.dto;

import java.time.LocalDateTime;

public record NoticeSummaryResponse(
        Long id,
        String title,
        int viewCount,
        LocalDateTime createdAt
) {}
