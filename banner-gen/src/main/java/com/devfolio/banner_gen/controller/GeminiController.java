package com.devfolio.banner_gen.controller;


import com.devfolio.banner_gen.model.*;
import com.devfolio.banner_gen.service.GeminiService;
import com.devfolio.banner_gen.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    private final RestTemplate restTemplate = new RestTemplate();

    String apiUrl = "http://localhost:8001";

    String baseUrl = "https://storage.googleapis.com/banner-bucket-001/";

    @PostMapping("/chat")
    public List<GeminiResponse> chatWithGemini(@RequestBody GeminiRequest request) {
        List<GeminiResponse> response = geminiService.getGeminiResponse(request.getMessage());
        return response;
    }


    @GetMapping("/get-banner-url")
    public String getBannerUrl (@RequestParam String imageName) {
        return storageService.getImageUrl(imageName);
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
        List<String> list = new ArrayList<>();
        if (bannerRequest.getProductImages() != null && !bannerRequest.getProductImages().isEmpty()) {
            for (ProductImages productImage : bannerRequest.getProductImages()) {
               String url = baseUrl+productImage.getPath();
               System.out.println(url);
               list.add(url);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LLMResponse requestBody = new LLMResponse();
        requestBody.setSelectedItem(bannerRequest.getSelectedItem());
        requestBody.setPromotionalOffers(bannerRequest.getPromotionalOffers());
        requestBody.setListOfProductImages(list);
        HttpEntity<LLMResponse> entity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
        bannerResponse.setUrl(list);//replace this with response url from llm
        return ResponseEntity.ok(bannerResponse);
      }



}
