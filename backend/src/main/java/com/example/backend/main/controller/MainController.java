package com.example.backend.main.controller;

import com.example.backend.global.annotation.ApiErrorExceptionsExample;
import com.example.backend.main.document.MainGetHomeExceptionDocs;
import com.example.backend.main.dto.MainPingResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@Tag(name = "Main Controller", description = "메인화면 API")
public class MainController {

    @GetMapping("/ping")
    @ApiErrorExceptionsExample(MainGetHomeExceptionDocs.class)
    public MainPingResponse ping() {
        return new MainPingResponse("OK");
    }
}
