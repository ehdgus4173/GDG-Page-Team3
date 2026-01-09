package com.example.backend.news.dto;

import com.example.backend.news.enums.NewsFileType;
import java.util.List;

public record CreateNewsRequest(
                String title,
                String content,
                List<FileDto> files) {
        public record FileDto(
                        String fileUrl,
                        NewsFileType fileType) {
        }
}
