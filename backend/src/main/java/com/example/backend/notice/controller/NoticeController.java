package com.example.backend.notice.controller;

import com.example.backend.common.CreateResponse;
import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.notice.document.*;
import com.example.backend.notice.dto.*;
import com.example.backend.notice.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    // 추후 notice service 작성
    private final NoticeService noticeService;

    @GetMapping
    @ApiErrorExceptionsExample(NoticeGetListExceptionDocs.class)
    public ResponseEntity<List<NoticeSummaryResponse>> getNoticeList() {
        return ResponseEntity.ok(noticeService.getNoticeList());
    }

    @GetMapping("/{id}")
    @ApiErrorExceptionsExample(NoticeGetDetailExceptionDocs.class)
    public ResponseEntity<NoticeDetailResponse> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    @Valid
    @PostMapping
    @ApiErrorExceptionsExample(NoticeCreateExceptionDocs.class)
    public ResponseEntity<CreateResponse> createNotice(@RequestBody CreateNoticeRequest request) {
        Long id = noticeService.createNotice(request);
        return ResponseEntity.status(201).body(new CreateResponse(id, true));
    }
}
