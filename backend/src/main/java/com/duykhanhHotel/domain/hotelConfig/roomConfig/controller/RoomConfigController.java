package com.duykhanhHotel.domain.hotelConfig.roomConfig.controller;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomConfigDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomPriceDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.service.RoomConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("room-configs")
@CrossOrigin
public class RoomConfigController {
    private final RoomConfigService roomConfigService;

    @GetMapping
    public RoomConfigDto.MultipleRoomConfig getListRoomConfig() {
        return RoomConfigDto.MultipleRoomConfig.builder().
                roomConfigs(roomConfigService.getListRoomConfig()).
                build();
    }

    @PostMapping
    public RoomConfigDto.SingleRoomConfig addRoomConfig(@RequestBody RoomConfigDto.CreateRoomConfig roomConfig) {
        return RoomConfigDto.SingleRoomConfig.builder().
                roomConfig(roomConfigService.addRoomConfig(roomConfig))
                .build();
    }

    @PutMapping("/{id}")
    public RoomConfigDto.SingleRoomConfig updateRoomConfig(@PathVariable("id") Long id,
                                          @RequestBody RoomConfigDto.UpdateRoomConfig roomConfig) {
        return RoomConfigDto.SingleRoomConfig.builder().
                roomConfig(roomConfigService.updateRoomConfig(id, roomConfig))
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteRoomConfig(@PathVariable("id") Long id) {
        roomConfigService.deleteRoomConfig(id);
    }
}
