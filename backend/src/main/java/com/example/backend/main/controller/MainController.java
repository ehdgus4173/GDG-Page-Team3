package com.example.backend.main.controller;

import com.example.backend.main.dto.MainPingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @GetMapping("/ping")
    public MainPingResponse ping() {
        return new MainPingResponse("OK");
    }
}
