package com.example.backend.profile.dto;

import com.example.backend.common.SnsLinks;
import com.example.backend.member.dto.MemberSnsLinkRequest;
import com.example.backend.member.enums.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyProfileRequest {

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "기수", example = "4")
    private int generation;

    @Schema(description = "역할", example = "CORE")
    private MemberRole role;

    @Schema(description = "파트", example = "AI")
    private String part;

    @Schema(description = "학번", example = "20240001")
    private String studentId;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/img.png")
    private String imageUrl;

    @Size(max=200)
    @Schema(description = "한 줄 소개", example = "머신러닝을 공부합니다.")
    private String bio;

    @Schema(description = "학과", example = "컴퓨터공학과")
    private String department;

    @Schema(description = "기술 스택 이름 목록", example = "[\"Java\", \"Spring\", \"React\"]")
    private List<String> techStacks;

    @Valid
    @Schema(description = "SNS 링크 목록")
    private List<MemberSnsLinkRequest> snsLinks;

}