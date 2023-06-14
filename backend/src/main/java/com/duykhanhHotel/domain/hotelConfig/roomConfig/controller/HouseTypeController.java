package com.duykhanhHotel.domain.hotelConfig.roomConfig.controller;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.HouseTypeDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.service.HouseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/house-types")
@CrossOrigin
public class HouseTypeController {
    private final HouseTypeService houseTypeService;

    @GetMapping
    public HouseTypeDto.MultipleHouseType getListHouseType() {
        return HouseTypeDto.MultipleHouseType.builder().houseTypes(houseTypeService.getListHouseType()).build();
    }

    @PostMapping
    public HouseTypeDto.SingleHouseType addHouseType(@RequestBody HouseTypeDto.Update dto) {
        return houseTypeService.addHouseType(dto);
    }

    @PutMapping("/{id}")
    public void updateHouseType(@PathVariable("id") Long id,
                                @RequestBody HouseTypeDto.Update dto) {
        houseTypeService.updateHouseType(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteHouseType(@PathVariable("id") Long id) {
        houseTypeService.deleteHouseType(id);
    }
}
