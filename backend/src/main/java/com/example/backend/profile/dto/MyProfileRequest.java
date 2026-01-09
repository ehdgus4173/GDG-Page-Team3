package com.example.backend.profile.dto;

import com.example.backend.common.SnsLinks;
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

    @Size(max=200)
    private String bio;

    private List<String> techStacks;

    @Valid
    private SnsLinks snsLinks;

}