package com.example.backend.notice.service;

import com.example.backend.notice.dto.CreateNoticeRequest;
import com.example.backend.notice.dto.NoticeDetailResponse;
import com.example.backend.notice.dto.NoticeSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Stub service to satisfy dependency; replace with real implementation later.
 */
@Service
public class NoticeService {

    public List<NoticeSummaryResponse> getNoticeList() {
        return Collections.emptyList();
    }

    public NoticeDetailResponse getNotice(Long id) {
        return null;
    }

    public Long createNotice(CreateNoticeRequest request) {
        return 0L;
    }
}
