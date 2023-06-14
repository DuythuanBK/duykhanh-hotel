package com.duykhanhHotel.domain.hotelOperating.room.controller;

import com.duykhanhHotel.domain.hotelOperating.room.dto.RoomDto;
import com.duykhanhHotel.domain.hotelOperating.room.model.RoomQueryParam;
import com.duykhanhHotel.domain.hotelOperating.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
@CrossOrigin
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public RoomDto.MultipleRoom getListRoom(@ModelAttribute RoomQueryParam param) {
        return RoomDto.MultipleRoom.builder().rooms(roomService.getListRoom(param)).build();
    }

    @GetMapping("/{fullName}")
    public RoomDto.SingleRoom getRoom(@PathVariable("fullName") String fullName) {
        return RoomDto.SingleRoom.builder().room(roomService.getRoom(fullName)).build();
    }

    @PutMapping("/{fullName}")
    public RoomDto.SingleRoom updateRoom(@PathVariable("fullName") String fullName,
                              @RequestBody RoomDto.UpdateRoom dto) {
        return RoomDto.SingleRoom.builder().room(roomService.updateRoom(fullName, dto)).build();
    }

    @PostMapping("/{fullName}/check-in")
    public RoomDto.SingleRoom checkinRoom(@PathVariable("fullName") String fullName) {
        return RoomDto.SingleRoom.builder().room(roomService.checkIn(fullName)).build();
    }

    @PostMapping("/{fullName}/check-out")
    public RoomDto.SingleRoom checkoutRoom(@PathVariable("fullName") String fullName) {
        return RoomDto.SingleRoom.builder().room(roomService.checkOut(fullName)).build();
    }
}
