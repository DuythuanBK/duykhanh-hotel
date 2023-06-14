package com.duykhanhHotel.domain.hotelConfig.roomConfig.service;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomPriceDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomPriceEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.RoomPriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoomPriceService {
    private final RoomPriceRepository repository;

    public RoomPriceDto.SingleRoomPrice addRoomPrice(RoomPriceDto.Update dto) {
        if(repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Room price name is already existed");
        }
        log.info("dto: " + dto.getName() + " " + dto.getFirstHour() + " " + dto.getOvernight());
        RoomPriceEntity entity = RoomPriceEntity
                .builder()
                .name(dto.getName())
                .firstHour(dto.getFirstHour())
                .nextHour(dto.getNextHour())
                .overnight(dto.getOvernight())
                .build();

        entity = repository.save(entity);
        log.info("entity: " + entity.getName() + " " + entity.getFirstHour() + " " + entity.getOvernight());

        return RoomPriceDto.SingleRoomPrice.builder()
                .roomPrice(convertEntityToDto(entity))
                .build();
    }

    public List<RoomPriceDto> getListRoomPrice() {
        List<RoomPriceEntity> entities = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        List<RoomPriceDto> dtos = new ArrayList<>();

        entities.stream().forEach((entity) -> {
            RoomPriceDto dto = convertEntityToDto(entity);

            dtos.add(dto);
        });

        return dtos;
    }

    public RoomPriceDto.SingleRoomPrice updateRoomPrice(Long id, RoomPriceDto.Update dto) {
        RoomPriceEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Price is not existed"));

        if(dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if(dto.getFirstHour() != null) {
            entity.setFirstHour(dto.getFirstHour());
        }

        if(dto.getNextHour() != null) {
            entity.setNextHour(dto.getNextHour());
        }

        if(dto.getOvernight() != null) {
            entity.setOvernight(dto.getOvernight());
        }


        entity = repository.save(entity);

        return RoomPriceDto.SingleRoomPrice.builder()
                .roomPrice(convertEntityToDto(entity))
                .build();
    }

    public void deleteRoomPrice(Long id) {
        RoomPriceEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Price is not existed"));

        repository.delete(entity);
    }

    private RoomPriceDto convertEntityToDto(RoomPriceEntity entity) {
        return RoomPriceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .firstHour(entity.getFirstHour())
                .nextHour(entity.getNextHour())
                .overnight(entity.getOvernight())
                .build();
    }
}
