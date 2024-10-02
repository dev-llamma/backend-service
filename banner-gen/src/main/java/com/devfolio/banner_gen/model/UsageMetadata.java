package com.devfolio.banner_gen.model;

import lombok.Data;

@Data
public class UsageMetadata {

    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
}
