package com.duykhanhHotel.domain.hotelConfig.roomConfig.controller;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomTypeDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/room-types")
@CrossOrigin
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @PostMapping
    public RoomTypeDto.SingleRoomType addRoomType(@RequestBody RoomTypeDto.Update dto) {
        return roomTypeService.addRoomType(dto);
    }

    @PutMapping("/{id}")
    public void updateRoomType(@PathVariable("id") Long id,
                               @RequestBody RoomTypeDto.Update dto) {
        roomTypeService.updateRoomType(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomType(@PathVariable("id") Long id) {
        roomTypeService.deleteRoomType(id);
    }

    @GetMapping
    public RoomTypeDto.MultipleRoomType getListRoomType() {
        return RoomTypeDto.MultipleRoomType.builder().roomTypes(roomTypeService.getListRoomType()).build();
    }
}
