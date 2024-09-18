package com.devfolio.banner_gen.controller;


import com.devfolio.banner_gen.model.GeminiRequest;
import com.devfolio.banner_gen.service.GeminiService;
import jakarta.servlet.annotation.WebListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.WebSocket;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/chat")
    public String chatWithGemini(@RequestBody GeminiRequest request) {
        return geminiService.getGeminiResponse(request.getMessage());
    }


}
