package com.example.backend.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberListRequest {

    /**
     * 선택 필터: 기수
     */
    @Schema(description = "기수 필터 (선택)", example = "4")
    private Integer generation;

    /**
     * 선택 필터: 파트
     */
    @Schema(description = "파트 필터 (선택)", example = "AI")
    private String part;

    /**
     * 페이지 번호 (0 기반), 미지정 시 0
     */
    @Schema(description = "페이지 번호(0부터 시작)", example = "0", defaultValue = "0")
    private Integer page;
}
