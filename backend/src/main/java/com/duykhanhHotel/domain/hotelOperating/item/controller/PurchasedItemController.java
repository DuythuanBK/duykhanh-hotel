package com.duykhanhHotel.domain.hotelOperating.item.controller;
import lombok.AllArgsConstructor;

import com.duykhanhHotel.domain.hotelOperating.item.dto.PurchasedItemDto;
import com.duykhanhHotel.domain.hotelOperating.item.service.PurchasedItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms/{fullName}/items")
@AllArgsConstructor
@CrossOrigin
public class PurchasedItemController {
    private final PurchasedItemService purchasedItemService;

    @GetMapping
    public PurchasedItemDto.MultipleItem getListPurchasedItem(@PathVariable("fullName") String fullName) {
        return PurchasedItemDto.MultipleItem.builder().items(purchasedItemService.getListPurchasedItem(fullName)).build();
    }

    @PostMapping
    public PurchasedItemDto.SingleItem addPurchasedItem(@PathVariable("fullName") String fullName,
                                 @RequestBody PurchasedItemDto.CreatedItem dto) {
        return purchasedItemService.addPurchasedItem(fullName, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePurchasedItem(@PathVariable("fullName") String fullName,
                                    @PathVariable("id") Long id) {
        purchasedItemService.deletePurchasedItem(id);
    }

    @PutMapping("/{id}")
    public void updatePurchasedItem(@PathVariable("fullName") String fullName,
                                    @PathVariable("id") Long id,
                                    @RequestBody PurchasedItemDto.UpdatedItem dto) {
        purchasedItemService.updatePurchasedItem(fullName, id, dto);
    }



}
