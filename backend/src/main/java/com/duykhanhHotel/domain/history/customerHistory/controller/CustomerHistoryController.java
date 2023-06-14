package com.duykhanhHotel.domain.history.customerHistory.controller;

import com.duykhanhHotel.domain.history.customerHistory.dto.CustomerHistoryDto;
import com.duykhanhHotel.domain.history.customerHistory.service.CustomerHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/history/:room_id/customer")
@CrossOrigin
public class CustomerHistoryController {
    private final CustomerHistoryService customerHistoryService;

    @GetMapping
    public CustomerHistoryDto getCustomerHistory(@PathVariable("room_id") Long roomId) {
        return customerHistoryService.getCustomerHistoryByRoomId(roomId);
    }

}
