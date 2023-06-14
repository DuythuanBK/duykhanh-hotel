package com.duykhanhHotel.domain.hotelConfig.roomConfig.controller;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomPriceDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.service.RoomPriceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("room-prices")
@CrossOrigin
public class RoomPriceController {
    private final RoomPriceService roomPriceService;

    @GetMapping
    public RoomPriceDto.MultipleRoomPrice getListRoomPrice() {
        return RoomPriceDto.MultipleRoomPrice.builder().roomPrices(roomPriceService.getListRoomPrice()).build();
    }

    @PostMapping
    public RoomPriceDto.SingleRoomPrice addRoomPrice(@RequestBody RoomPriceDto.Update dto) {
        return roomPriceService.addRoomPrice(dto);
    }

    @PutMapping("/{id}")
    public RoomPriceDto.SingleRoomPrice updateRoomPrice(@PathVariable("id") Long id,
                                                        @RequestBody RoomPriceDto.Update dto) {
        return roomPriceService.updateRoomPrice(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomPrice(@PathVariable("id") Long id) {
        roomPriceService.deleteRoomPrice(id);
    }

}
