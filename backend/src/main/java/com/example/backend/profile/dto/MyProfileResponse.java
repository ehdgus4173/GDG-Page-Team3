package com.example.backend.profile.dto;

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
public class MyProfileResponse {

    private Long profileId;

    private String name;

    private int generation;

    private MemberRole role;

    private String part;

    @Size(max=200)
    private String bio;

    private String department;

    private String imageUrl;

    private List<TechStackResponse> techStacks;

    @Valid
    private SnsLinks snsLinks;
}
