package com.duykhanhHotel.domain.hotelOperating.item.dto;

import com.duykhanhHotel.domain.hotelConfig.item.dto.ItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class PurchasedItemDto {
    private Long id;
    private String name;
    private Integer price;
    private String type;
    private Integer quantity;

    @Data
    @Builder
    @JsonTypeName("item")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class CreatedItem {
        @JsonProperty("name")
        private String name;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("type")
        private String type;
        @JsonProperty("quantity")
        private Integer quantity;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("item")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class UpdatedItem {
        @JsonProperty("quantity")
        private Integer quantity;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class SingleItem {
        private PurchasedItemDto item;
    }

    @Data
    @Builder
    @JsonTypeName("items")
    public static class MultipleItem {
        private List<PurchasedItemDto> items;
    }
}
