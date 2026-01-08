package com.example.backend.notice.service;

import com.example.backend.notice.dto.CreateNoticeRequest;
import com.example.backend.notice.dto.NoticeDetailResponse;
import com.example.backend.notice.dto.NoticeSummaryResponse;

import java.util.List;

public interface NoticeService {
    List<NoticeSummaryResponse> getNoticeList();
    NoticeDetailResponse getNotice(Long id);
    Long createNotice(CreateNoticeRequest request);
}
