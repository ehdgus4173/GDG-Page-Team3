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

    private Long profileId;

    private String name;

    private int generation;

    private String part;

    private String imageUrl;
}
