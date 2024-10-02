package com.devfolio.banner_gen.model;

import lombok.Data;

import java.util.List;
@Data
public class LLMRequest {

    List<String> listOfProductImages;

    List<String> promotionalOffers;

    ThemeAndPalette themeAndPalette;

    Header selectedItem;
}
