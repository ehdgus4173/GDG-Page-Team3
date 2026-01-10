package com.example.backend.notice.dto;

import com.example.backend.notice.enums.NoticeFileType;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        int viewCount,
        LocalDateTime createdAt,
        List<FileDto> files,
        Long authorId,
        PostNavigationDto prevPost,
        PostNavigationDto nextPost) {
    public record FileDto(
            Long id,
            String fileUrl,
            NoticeFileType fileType) {
    }

    public record PostNavigationDto(
            Long id,
            String title) {
    }
}
