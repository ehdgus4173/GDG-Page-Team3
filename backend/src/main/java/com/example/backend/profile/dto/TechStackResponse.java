package com.example.backend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TechStackResponse {

    private Long id;
    private String name;
    private String iconUrl;
}
