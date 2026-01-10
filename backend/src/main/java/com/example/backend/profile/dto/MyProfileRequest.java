package com.example.backend.profile.dto;

import com.example.backend.common.SnsLinks;
import com.example.backend.member.enums.MemberRole;
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

    private String name;

    private int generation;

    private MemberRole role;

    private String part;

    private String studentId;

    private String imageUrl;

    @Size(max=200)
    private String bio;

    private String department;

    private List<String> techStacks;

    @Valid
    private SnsLinks snsLinks;

}