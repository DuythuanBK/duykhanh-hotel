package com.duykhanhHotel.domain.hotelConfig.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private Integer price;
    private String type;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonTypeName("item")
    public static class SingleItem {
        private ItemDto item;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("items")
    public static class MultipleItems {
        private List<ItemDto> items;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("item")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class UpdateItem {
        @JsonProperty("name")
        private String name;
        @JsonProperty("price")
        private Integer price;
        private String type;
    }
}
