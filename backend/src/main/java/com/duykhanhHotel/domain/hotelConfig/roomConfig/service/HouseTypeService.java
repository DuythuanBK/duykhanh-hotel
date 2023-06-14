package com.duykhanhHotel.domain.hotelConfig.roomConfig.service;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.HouseTypeDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.HouseTypeEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.HouseTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HouseTypeService {
    private final HouseTypeRepository repositoty;

    public HouseTypeDto.SingleHouseType addHouseType(HouseTypeDto.Update dto) {
        if(repositoty.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("House Type is already existed");
        }

        HouseTypeEntity entity = HouseTypeEntity
                .builder()
                .name(dto.getName())
                .build();

        entity = repositoty.save(entity);

        return HouseTypeDto.SingleHouseType.builder()
                .houseType(convertToDto(entity))
                .build();
    }

    public void updateHouseType(Long id, HouseTypeDto.Update dto) {
        HouseTypeEntity entity = repositoty.findById(id)
                .orElseThrow(() -> new RuntimeException("House Type is not existed"));

        if(dto.getName() != null) {
            entity.setName(dto.getName());
        }

        repositoty.save(entity);
    }

    public void deleteHouseType(Long id) {
        HouseTypeEntity entity = repositoty.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Type is not existed"));

        repositoty.delete(entity);
    }

    public List<HouseTypeDto> getListHouseType() {
        List<HouseTypeEntity> houseTypeEntitys = repositoty.findAll();

        List<HouseTypeDto> houseTypeDtos = new ArrayList<>();

        houseTypeEntitys.stream().forEach((entity) -> {
            HouseTypeDto dto = convertToDto(entity);
            houseTypeDtos.add(dto);
        });

        return houseTypeDtos;
    }

    private HouseTypeDto convertToDto(HouseTypeEntity entity) {
        return HouseTypeDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
