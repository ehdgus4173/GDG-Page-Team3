package com.example.backend.notice.dto;

import com.example.backend.notice.enums.NoticeFileType;
import java.util.List;

public record CreateNoticeRequest(
        String title,
        String content,
        List<FileDto> files) {
    public record FileDto(
            String fileUrl,
            NoticeFileType fileType) {
    }
}
