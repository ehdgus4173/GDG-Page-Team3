package com.example.backend.profile.dto;

import com.example.backend.common.SnsLinks;
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

    private String part;

    private String imageUrl;

    private String bio;

    private List<TechStackResponse> techStacks;

    @Valid
    private SnsLinks snsLinks;
}
