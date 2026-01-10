package com.example.backend.member.dto;

import com.example.backend.member.enums.SnsType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSnsLinkRequest {

    @Schema(description = "SNS 타입", example = "GITHUB")
    private SnsType type;

    @Schema(description = "SNS URL", example = "https://github.com/example")
    private String url;
}
