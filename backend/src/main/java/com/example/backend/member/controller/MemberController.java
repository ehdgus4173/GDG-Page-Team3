package com.example.backend.member.controller;

import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.member.document.MemberLookupExceptionDocs;
import com.example.backend.member.dto.MemberDetailResponse;
import com.example.backend.member.dto.MemberListRequest;
import com.example.backend.member.dto.MemberListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "멤버 관련 API")
public class MemberController {

    // private final MemberService memberservice;

    @GetMapping
    @Operation(summary = "멤버 목록 조회")
    @ApiErrorExceptionsExample(MemberLookupExceptionDocs.class)
    public ResponseEntity<List<MemberListResponse>> getMembers(
            @Parameter(description = "검색 조건", required = false)
            MemberListRequest request) {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "멤버 상세 조회")
    @ApiErrorExceptionsExample(MemberLookupExceptionDocs.class)
    public ResponseEntity<MemberDetailResponse> getMemberDetail(
            @Parameter(description = "멤버 ID", example = "1", required = true)
            @PathVariable Long memberId) {
        // TODO: 서비스 로직 연결
        return ResponseEntity.ok().build();
    }
}
