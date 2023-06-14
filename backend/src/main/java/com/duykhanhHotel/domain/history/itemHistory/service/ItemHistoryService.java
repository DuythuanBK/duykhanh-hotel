package com.duykhanhHotel.domain.history.itemHistory.service;

import com.duykhanhHotel.domain.history.itemHistory.dto.ItemHistoryDto;
import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import com.duykhanhHotel.domain.history.itemHistory.repository.ItemHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemHistoryService {
    private final ItemHistoryRepository repository;

    public List<ItemHistoryDto> getItemHistoryList(Long roomId) {
        List<ItemHistoryEntity> itemHistoryEntities = repository.findByRoomHistoryId(roomId);

        List<ItemHistoryDto> dtos = new ArrayList<>();

        itemHistoryEntities.stream().forEach((entity) -> {
            ItemHistoryDto dto = convertItemHistoryEntityToDto(entity);
            dtos.add(dto);
        });

        return dtos;
    }

    private ItemHistoryDto convertItemHistoryEntityToDto(ItemHistoryEntity entity) {
        return ItemHistoryDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .name(entity.getName())
                .build();
    }
}
