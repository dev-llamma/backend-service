package com.devfolio.banner_gen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class BannerRequest {
    @JsonProperty
    List<ProductImages> productImages;
    @JsonProperty
    List<String> promotionalOffers;
    @JsonProperty
    ThemeAndPalette themeAndPalette;
    @JsonProperty
    String bannerSizeData;
    @JsonProperty
    Header selectedItem;
}
