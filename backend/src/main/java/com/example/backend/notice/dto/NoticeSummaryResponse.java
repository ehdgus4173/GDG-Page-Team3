package com.example.backend.notice.dto;

import java.time.LocalDateTime;

public record NoticeSummaryResponse(
                Long id,
                String title,
                String thumbnailUrl,
                int viewCount,
                LocalDateTime createdAt) {
}
