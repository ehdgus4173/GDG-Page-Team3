package com.example.backend.member.controller;

import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.member.document.MemberLookupExceptionDocs;
import com.example.backend.member.dto.MemberDetailResponse;
import com.example.backend.member.dto.MemberListRequest;
import com.example.backend.member.dto.MemberListResponse;
import com.example.backend.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "멤버 관련 API")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "멤버 목록 조회")
    @ApiErrorExceptionsExample(MemberLookupExceptionDocs.class)
    public ResponseEntity<List<MemberListResponse>> getMembers(
            @Parameter(description = "멤버 목록 검색 조건")
            @ModelAttribute MemberListRequest request
    ) {
        List<MemberListResponse> members =
                memberService.getMemberList(
                        request.getGeneration(),
                        request.getPart(),
                        request.getPage()
                );

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "멤버 상세 조회")
    @ApiErrorExceptionsExample(MemberLookupExceptionDocs.class)
    public ResponseEntity<MemberDetailResponse> getMemberDetail(
            @Parameter(description = "멤버 ID", example = "1", required = true)
            @PathVariable Long memberId
    ) {
        MemberDetailResponse response =
                memberService.getMemberDetail(memberId);

        return ResponseEntity.ok(response);
    }
}

