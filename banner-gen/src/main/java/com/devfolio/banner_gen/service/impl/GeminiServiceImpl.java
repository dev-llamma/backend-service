package com.devfolio.banner_gen.service.impl;

import com.devfolio.banner_gen.model.GeminiResponse;
import com.devfolio.banner_gen.model.GeminiResponseDTO;
import com.devfolio.banner_gen.service.GeminiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<GeminiResponse> getGeminiResponse(String userMessage) {
        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request body with user message and model configuration
            String requestBody = buildRequestBody(userMessage);

            // Set up entity
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Send POST request and get response
            ResponseEntity<GeminiResponseDTO> response = restTemplate.exchange(apiUrl + apiKey, HttpMethod.POST, entity, GeminiResponseDTO.class);
            GeminiResponseDTO responseBody = response.getBody();

            // Check if response has candidates and map to List of GeminiResponse
            if (responseBody != null && responseBody.getCandidates() != null) {
                return responseBody.getCandidates().stream()
                        .flatMap(candidate -> candidate.getContent().getParts().stream())
                        .flatMap(part -> mapToGeminiResponseList(part.getText()).stream()) // Assuming the text contains a list
                        .collect(Collectors.toList());
            }

            return List.of(); // Return an empty list if no valid response is found
        } catch (HttpClientErrorException e) {
            // Handle error, you can log the error or throw a custom exception
            throw new RuntimeException("Error occurred while calling Gemini API: " + e.getMessage());
        }
    }

    // Map the JSON string to a List of GeminiResponse objects
    private List<GeminiResponse> mapToGeminiResponseList(String jsonText) {
        try {
            // Assuming the part's text is in JSON array format, use Jackson to deserialize it into a list of GeminiResponse
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonText, new TypeReference<List<GeminiResponse>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }
    }
    private String buildRequestBody(String message) {
        return "{"
                + "\"contents\": ["
                + "    {\"role\": \"user\", \"parts\": [{\"text\": \"" + message + "\"}]}"
                + "],"
                + "\"systemInstruction\": {"
                + "    \"role\": \"user\","
                + "    \"parts\": [{\"text\": \"You are a banner and poster information generator. You are to provide catchy, creative, and quirky 4 header, subheader, and content lines in text. You can use current trends and theme-based information provided.\"}]"
                + "},"
                + "\"generationConfig\": {"
                + "    \"temperature\": 0.15,"
                + "    \"topK\": 64,"
                + "    \"topP\": 0.95,"
                + "    \"maxOutputTokens\": 8192,"
                + "    \"responseSchema\": {"
                + "        \"type\": \"array\","
                + "        \"items\": {"
                + "            \"type\": \"object\","
                + "            \"properties\": {"
                + "                \"header\": {"
                + "                    \"type\": \"string\""
                + "                },"
                + "                \"subheader\": {"
                + "                    \"type\": \"string\""
                + "                },"
                + "                \"content\": {"
                + "                    \"type\": \"string\""
                + "                }"
                + "            },"
                + "            \"required\": [\"header\", \"subheader\", \"content\"]"
                + "        }"
                + "    },"
                + "    \"responseMimeType\": \"application/json\""
                + "}"
                + "}";
    }
}
