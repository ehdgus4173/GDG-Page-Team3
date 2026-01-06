package com.example.backend.news.controller;

import com.example.backend.common.CreateResponse;
import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.news.document.*;
import com.example.backend.news.dto.*;
import com.example.backend.news.service.NewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    // Service layer 작업 시 추가
    private final NewsService newsService;

    @GetMapping
    @ApiErrorExceptionsExample(NewsGetListExceptionDocs.class)
    public ResponseEntity<List<NewsSummaryResponse>> getNewsList() {
        return ResponseEntity.ok(newsService.getNewsList());
    }

    @GetMapping("/{id}")
    @ApiErrorExceptionsExample(NewsGetDetailExceptionDocs.class)
    public ResponseEntity<NewsDetailResponse> getNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNews(id));
    }

    @Valid
    @PostMapping
    @ApiErrorExceptionsExample(NewsCreateExceptionDocs.class)
    public ResponseEntity<CreateResponse> createNews(@RequestBody CreateNewsRequest request) {
        Long id = newsService.createNews(request);
        return ResponseEntity.status(201).body(new CreateResponse(id, true));
    }
}
