package com.example.backend.news.dto;

import com.example.backend.news.enums.NewsFileType;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDetailResponse(
                Long id,
                String title,
                String content,
                LocalDateTime createdAt,
                int viewCount,
                String thumbnailUrl,
                int generation,
                List<FileDto> files,
                Long authorId) {
        public record FileDto(
                        Long id,
                        String fileUrl,
                        NewsFileType fileType) {
        }
}
