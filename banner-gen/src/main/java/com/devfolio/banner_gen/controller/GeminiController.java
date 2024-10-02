package com.devfolio.banner_gen.controller;


import com.devfolio.banner_gen.model.*;
import com.devfolio.banner_gen.service.GeminiService;
import com.devfolio.banner_gen.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/gemini")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/chat")
    public List<GeminiResponse> chatWithGemini(@RequestBody GeminiRequest request) {
        List<GeminiResponse> response = geminiService.getGeminiResponse(request.getMessage());
        return response;
    }


    @GetMapping("/get-banner-url")
    public String getBannerUrl () {
        return storageService.getImageUrl();
    }


    @PostMapping("/get-banner-data")
    public ResponseEntity<BannerResponse> getBannerData(@RequestBody BannerRequest bannerRequest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(bannerRequest);
            log.info(jsonRequest);
            System.out.println(jsonRequest);
        } catch (Exception e) {
            log.error("Error converting request to JSON", e);
        }
        BannerResponse bannerResponse = new BannerResponse();
        String imageUrl = "https://storage.googleapis.com/banner-bucket-001/GOPR1215.JPG";
        List<String> list= new ArrayList<>();
        list.add(imageUrl);
        bannerResponse.setUrl(list);
        return ResponseEntity.ok(bannerResponse);
      }


}
