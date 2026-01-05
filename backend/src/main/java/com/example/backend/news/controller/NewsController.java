package com.example.backend.news.controller;

import com.example.backend.global.dto.CreateResponse;
import com.example.backend.news.dto.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @GetMapping
    public ResponseEntity<List<NewsSummaryResponse>> getNewsList() {
        // TODO
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDetailResponse> getNews(@PathVariable Long id) {
        // TODO
        return ResponseEntity.ok(null);
    }

    @Valid
    @PostMapping
    public ResponseEntity<CreateResponse> createNews(@RequestBody CreateNewsRequest request) {
        // TODO
        return ResponseEntity.status(201).body(null);
    }
}
