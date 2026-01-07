package com.example.backend.profile.controller;

import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.profile.document.ProfileImageExceptionDocs;
import com.example.backend.profile.document.ProfileInfoExceptionDocs;
import com.example.backend.profile.document.ProfilePasswordExceptionDocs;
import com.example.backend.profile.dto.MyProfileRequest;
import com.example.backend.profile.dto.MyProfileResponse;
import com.example.backend.profile.dto.PasswordUpdateRequest;
import com.example.backend.profile.dto.ProfileImageUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Controller", description = "내 프로필 관리 API")
public class ProfileController {

    @GetMapping
    @Operation(summary = "내 프로필 조회")
    @ApiErrorExceptionsExample(ProfileInfoExceptionDocs.class)
    public ResponseEntity<MyProfileResponse> getMyProfile() {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "프로필 정보 수정")
    @ApiErrorExceptionsExample(ProfileInfoExceptionDocs.class)
    public ResponseEntity<Void> updateProfile(
            @Parameter(description = "수저할 프로필 정보", required = true)
            @RequestBody MyProfileRequest request) {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiErrorExceptionsExample(ProfilePasswordExceptionDocs.class)
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "비밀번호 변경 요청", required = true)
            @RequestBody PasswordUpdateRequest request) {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }

    @PostMapping("/image")
    @Operation(summary = "프로필 이미지 변경")
    @ApiErrorExceptionsExample(ProfileImageExceptionDocs.class)
    public ResponseEntity<ProfileImageUrlResponse> updateProfileImage(
            @Parameter(description = "프로필 이미지 파일", required = true)
            @RequestParam("file") MultipartFile file) {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }


}
