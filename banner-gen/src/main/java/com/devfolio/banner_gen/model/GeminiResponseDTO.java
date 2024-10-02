package com.devfolio.banner_gen.model;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponseDTO {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}
