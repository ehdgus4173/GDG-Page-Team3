package com.example.backend.notice.controller;

import com.example.backend.global.dto.CreateResponse;
import com.example.backend.notice.dto.CreateNoticeRequest;
import com.example.backend.notice.dto.NoticeDetailResponse;
import com.example.backend.notice.dto.NoticeSummaryResponse;
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
    public ResponseEntity<List<NoticeSummaryResponse>> getNoticeList() {
        // TODO
        return ResponseEntity.ok(noticeService.getNoticeList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> getNotice(@PathVariable Long id) {
        // TODO
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    @Valid
    @PostMapping
    public ResponseEntity<CreateResponse> createNotice(@RequestBody CreateNoticeRequest request) {
        // TODO
        Long id = noticeService.createNotice(request);
        return ResponseEntity.status(201).body(new CreateResponse(id, true));
    }
}
