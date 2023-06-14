package com.duykhanhHotel.domain.hotelConfig.roomConfig.service;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomTypeDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomTypeEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.RoomTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RoomTypeService {
    private final RoomTypeRepository repositoty;

    public RoomTypeDto.SingleRoomType addRoomType(RoomTypeDto.Update dto) {
        if(repositoty.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Room Type is already existed");
        }

        RoomTypeEntity entity = RoomTypeEntity.builder()
                .name(dto.getName())
                .build();

        entity = repositoty.save(entity);

        return RoomTypeDto.SingleRoomType.builder()
                .roomType(convertToDto(entity))
                .build();
    }

    public void updateRoomType(Long id, RoomTypeDto.Update dto) {
        RoomTypeEntity entity = repositoty.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Type is not existed"));

        if(dto.getName() != null) {
            entity.setName(dto.getName());
        }

        repositoty.save(entity);
    }

    public void deleteRoomType(Long id) {
        RoomTypeEntity entity = repositoty.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Type is not existed"));

        repositoty.delete(entity);
    }

    public List<RoomTypeDto> getListRoomType() {
        List<RoomTypeEntity> roomTypeEntities = repositoty.findAll();

        List<RoomTypeDto> roomTypeDtos = new ArrayList<>();

        roomTypeEntities.stream().forEach((entity) -> {
            RoomTypeDto dto = RoomTypeDto
                    .builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();

            roomTypeDtos.add(dto);
        });

        return roomTypeDtos;
    }

    private RoomTypeDto convertToDto(RoomTypeEntity entity) {
        return RoomTypeDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
