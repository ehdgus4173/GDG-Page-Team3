package com.example.backend.profile.controller;

import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.profile.document.ProfileImageExceptionDocs;
import com.example.backend.profile.document.ProfileInfoExceptionDocs;
import com.example.backend.profile.document.ProfilePasswordExceptionDocs;
import com.example.backend.profile.dto.*;
import com.example.backend.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Controller", description = "내 프로필 관리 API")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "내 프로필 조회")
    @ApiErrorExceptionsExample(ProfileInfoExceptionDocs.class)
    public ResponseEntity<MyProfileResponse> getMyProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MyProfileResponse response = profileService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "프로필 정보 수정")
    @ApiErrorExceptionsExample(ProfileInfoExceptionDocs.class)
    public ResponseEntity<Void> updateProfile(
            @Parameter(description = "수정할 프로필 정보", required = true)
            @Valid @RequestBody MyProfileRequest request,
            Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        profileService.updateMyProfile(userId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiErrorExceptionsExample(ProfilePasswordExceptionDocs.class)
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "비밀번호 변경 요청", required = true)
            @Valid @RequestBody PasswordUpdateRequest request,
            Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        profileService.updatePassword(userId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/image")
    @Operation(summary = "프로필 이미지 변경")
    @ApiErrorExceptionsExample(ProfileImageExceptionDocs.class)
    public ResponseEntity<ProfileImageUrlResponse> updateProfileImage(
            @Parameter(description = "이미지 URL 정보", required = true)
            @Valid @RequestBody ProfileImageUrlRequest request,
            Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        ProfileImageUrlResponse response = profileService.updateProfileImage(userId, request.getImageUrl());
        return ResponseEntity.ok(response);
    }
}