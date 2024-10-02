package com.devfolio.banner_gen.model;

import lombok.Data;

import java.util.List;
@Data
public class LLMResponse {

    List<String> listOfProductImages;

    List<String> promotionalOffers;

    Header selectedItem;
}
