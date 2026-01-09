package com.example.backend.member.dto;

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
public class MemberDetailResponse {

    @NotNull
    private Long profileId;

    @NotBlank
    private String name;

    @NotNull
    private int generation;

    @NotBlank
    private String part;

    private String imageUrl;

    @Size(max = 200)
    private String introduction;

    private List<String> techStacks;

    @Valid
    private SnsLinks snsLinks;
}
