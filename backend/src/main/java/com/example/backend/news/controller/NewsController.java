package com.example.backend.news.controller;

import com.example.backend.global.dto.CreateResponse;
import com.example.backend.news.dto.*;
//import com.example.backend.news.service.NewsService;

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
    public ResponseEntity<List<NewsSummaryResponse>> getNewsList() {
        // TODO
        return ResponseEntity.ok(newsService.getNewsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDetailResponse> getNews(@PathVariable Long id) {
        // TODO
        return ResponseEntity.ok(newsService.getNews(id));
    }

    @Valid
    @PostMapping
    public ResponseEntity<CreateResponse> createNews(@RequestBody CreateNewsRequest request) {
        // TODO
        Long id = newsService.createNews(request);
        return ResponseEntity.status(201).body(new CreateResponse(id, true));
    }
}
