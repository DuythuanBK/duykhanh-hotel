package com.duykhanhHotel.domain.history.roomHistory.controller;

import com.duykhanhHotel.domain.history.roomHistory.dto.RoomHistoryDto;
import com.duykhanhHotel.domain.history.roomHistory.model.RoomHistoryParam;
import com.duykhanhHotel.domain.history.roomHistory.service.RoomHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/history/rooms")
@CrossOrigin
public class RoomHistoryController {
    private final RoomHistoryService roomHistoryService;

    @GetMapping
    public RoomHistoryDto.MultipleRoomHistory getRoomHistoryList(@ModelAttribute RoomHistoryParam param) {
        return roomHistoryService.getRoomHistoryList(param);
    }
}
