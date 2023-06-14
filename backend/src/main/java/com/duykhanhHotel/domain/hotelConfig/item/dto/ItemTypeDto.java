package com.duykhanhHotel.domain.hotelConfig.item.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.servlet.http.PushBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemTypeDto {
    private Long id;
    private String name;

    @Data
    @Builder
    @JsonTypeName("itemType")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        private String name;
    }

    @Data
    @Builder
    @JsonTypeName("itemTypes")
    public static class MultipleItemType {
        private List<ItemTypeDto> itemTypes;
    }
}
