package com.example.backend.member.dto;

import com.example.backend.member.enums.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {

    @Schema(description = "회원 ID", example = "1")
    private Long userId;

    @Schema(description = "프로필 ID", example = "10")
    private Long profileId;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "역할", example = "CORE")
    private MemberRole role;

    @Schema(description = "파트", example = "AI")
    private String part;

    @Schema(description = "학과", example = "컴퓨터공학과")
    private String department;

    @Schema(description = "한 줄 소개", example = "머신러닝을 공부하는 4기 코어")
    private String bio;

    @Schema(description = "기수", example = "4")
    private int generation;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "기술 스택 목록", example = "[\"Python\", \"PyTorch\", \"React\"]")
    private List<String> techStacks;
}
