package com.example.backend.member.dto;

import com.example.backend.common.SnsLinks;
import com.example.backend.member.enums.MemberRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponse {

    private Long profileId;

    private String name;

    private MemberRole role;

    private int generation;

    private String part;

    private String imageUrl;

    private String bio;

    private List<String> techStacks;

    private SnsLinks snsLinks;
}
