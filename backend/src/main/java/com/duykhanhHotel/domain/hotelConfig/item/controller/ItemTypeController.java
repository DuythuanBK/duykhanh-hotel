package com.duykhanhHotel.domain.hotelConfig.item.controller;

import com.duykhanhHotel.domain.hotelConfig.item.dto.ItemTypeDto;
import com.duykhanhHotel.domain.hotelConfig.item.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/item_types")
@CrossOrigin
public class ItemTypeController {
    private final ItemService itemService;

    @PostMapping
    public void createItemType(@RequestBody ItemTypeDto.Update dto) {
        itemService.createItemType(dto);
    }

    @GetMapping
    public ItemTypeDto.MultipleItemType getListItemType() {
        return ItemTypeDto.MultipleItemType.builder().itemTypes(itemService.getListItemType()).build();
    }

    @PutMapping("/{id}")
    public void updateItemType(@PathVariable("id") Long id, @RequestBody ItemTypeDto.Update dto) {
        itemService.updateItemType(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteItemType(@PathVariable("id") Long id) {
        itemService.deleteItemType(id);
    }
}
