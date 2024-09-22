package com.devfolio.banner_gen.service.impl;

import com.devfolio.banner_gen.service.GeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGeminiResponse(String userMessage) {
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request body with user message and model configuration
        String requestBody = buildRequestBody(userMessage);

        // Set up entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + apiKey, HttpMethod.POST, entity, String.class);

        // Return the response text
        return response.getBody();
    }

    private String buildRequestBody(String message) {
        return "{"
                + "\"contents\": ["
                + "    {\"role\": \"user\", \"parts\": [{\"text\": \"" + message + "\"}]},"
                + "],"
                + "\"systemInstruction\": {"
                + "    \"role\": \"user\","
                + "    \"parts\": [{\"text\": \"You are a banner and poster information generator. You are to provide catchy, creative, and quirky header lines and text elements based on the product provided. You can use current trends and theme-based information provided.\"}]"
                + "},"
                + "\"generationConfig\": {"
                + "    \"temperature\": 0.15,"
                + "    \"topK\": 64,"
                + "    \"topP\": 0.95,"
                + "    \"maxOutputTokens\": 8192,"
                + "    \"responseMimeType\": \"text/plain\""
                + "}"
                + "}";
    }
}
