package com.devfolio.banner_gen.service;

import com.devfolio.banner_gen.model.GeminiResponse;
import com.devfolio.banner_gen.model.GeminiResponseDTO;

import java.util.List;

public interface GeminiService {

    List<GeminiResponse> getGeminiResponse(String userMessage);
}
