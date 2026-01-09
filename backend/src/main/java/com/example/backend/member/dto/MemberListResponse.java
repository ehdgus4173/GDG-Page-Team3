package com.example.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {

    @NotNull
    private Long profileId;

    @NotBlank
    private String name;

    @NotNull
    private int generation;

    @NotBlank
    private String part;

    private String imageUrl;
}
