package com.duykhanhHotel.domain.history.itemHistory.controller;

import com.duykhanhHotel.domain.history.itemHistory.dto.ItemHistoryDto;
import com.duykhanhHotel.domain.history.itemHistory.service.ItemHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/history/:room_id/orders")
@CrossOrigin
public class ItemHistoryController {
    private final ItemHistoryService itemHistoryService;

    @GetMapping
    public ItemHistoryDto.MultipleItem getItemHistoryList(@PathVariable("room_id") Long roomId) {
        return ItemHistoryDto.MultipleItem.builder()
                .items(itemHistoryService.getItemHistoryList(roomId)).build();
    }
}
