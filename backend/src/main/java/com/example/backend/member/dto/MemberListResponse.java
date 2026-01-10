package com.example.backend.member.dto;

import com.example.backend.member.enums.MemberRole;
import com.example.backend.member.enums.SnsType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {

    private Long profileId;

    private String name;

    private MemberRole role;

    private String part;

    private String department;

    private String bio;

    private int generation;

    private String imageUrl;

    private List<String> techStacks;

}
