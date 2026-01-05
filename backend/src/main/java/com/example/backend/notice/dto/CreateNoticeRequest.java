package com.example.backend.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNoticeRequest(
        @NotBlank
        @Size(max=200)
        String title,

        @NotBlank
        String content
) {}
