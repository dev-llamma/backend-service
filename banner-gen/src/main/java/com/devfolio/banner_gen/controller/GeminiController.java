package com.devfolio.banner_gen.controller;


import com.devfolio.banner_gen.model.BannerRequest;
import com.devfolio.banner_gen.model.GeminiRequest;
import com.devfolio.banner_gen.service.GeminiService;
import com.devfolio.banner_gen.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/chat")
    public String chatWithGemini(@RequestBody GeminiRequest request) {
        return geminiService.getGeminiResponse(request.getMessage());
    }


    @GetMapping("/get-banner-url")
    public String getBannerUrl () {
        return storageService.getImageUrl();
    }


    @GetMapping("/get-banner-data")
    public HttpStatus getBannerData(@RequestBody BannerRequest bannerRequest)
    {
        return HttpStatus.OK;
    }


}
