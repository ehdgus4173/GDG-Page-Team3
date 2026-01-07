package com.example.backend.news.service;

import com.example.backend.news.dto.CreateNewsRequest;
import com.example.backend.news.dto.NewsDetailResponse;
import com.example.backend.news.dto.NewsSummaryResponse;

import java.util.List;

public interface NewsService {
    List<NewsSummaryResponse> getNewsList();
    NewsDetailResponse getNews(Long id);
    Long createNews(CreateNewsRequest request);
}
