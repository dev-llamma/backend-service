package com.devfolio.banner_gen.model;

import lombok.Data;

import java.util.List;

@Data
public class Candidate {

    private Content content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;

}
