package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BuyCardShopSummaryDto {

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;


    @JsonProperty("BuyCardDtos")
    private List<BuyCardDto> BuyCardDtos;

}
