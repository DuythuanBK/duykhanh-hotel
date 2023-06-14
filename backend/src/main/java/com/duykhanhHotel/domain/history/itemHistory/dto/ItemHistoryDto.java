package com.duykhanhHotel.domain.history.itemHistory.dto;

import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemHistoryDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class MultipleItem {
        private List<ItemHistoryDto> items;
    }
}
