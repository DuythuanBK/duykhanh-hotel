package com.duykhanhHotel.domain.hotelConfig.item.controller;

import com.duykhanhHotel.domain.hotelConfig.item.dto.ItemDto;
import com.duykhanhHotel.domain.hotelConfig.item.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto.SingleItem createItem(@RequestBody ItemDto.UpdateItem itemDto) {
        return itemService.createItem(itemDto);
    }

    @GetMapping
    public ItemDto.MultipleItems getListItem() {
        return ItemDto.MultipleItems.builder().items(itemService.getListItem()).build();
    }

    @GetMapping("/{id}")
    public ItemDto.SingleItem getItem(@PathVariable("id") Long id) {

        return ItemDto.SingleItem.builder().item(itemService.getItem(id)).build();
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable("id") Long id,
                           @RequestBody ItemDto.UpdateItem itemDto) {
        itemService.updateItem(id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
    }
}
