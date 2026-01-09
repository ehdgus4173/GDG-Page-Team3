package com.example.backend.news.service;

import com.example.backend.news.dto.CreateNewsRequest;
import com.example.backend.news.dto.NewsDetailResponse;
import com.example.backend.news.dto.NewsSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Stub service to satisfy dependency; replace with real implementation later.
 */
@Service
public class NewsService {

    public List<NewsSummaryResponse> getNewsList() {
        return Collections.emptyList();
    }

    public NewsDetailResponse getNews(Long id) {
        return null;
    }

    public Long createNews(CreateNewsRequest request) {
        return 0L;
    }
}
