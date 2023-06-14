package com.duykhanhHotel.domain.hotelConfig.roomConfig.service;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.dto.RoomConfigDto;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.HouseTypeEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomConfigEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomPriceEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomTypeEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.HouseTypeRepository;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.RoomConfigRepository;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.RoomPriceRepository;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.repository.RoomTypeRepository;
import com.duykhanhHotel.domain.hotelOperating.customer.entity.CustomerEntity;
import com.duykhanhHotel.domain.hotelOperating.customer.repository.CustomerRepository;
import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import com.duykhanhHotel.domain.hotelOperating.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Data
@Service
public class RoomConfigService {
    private final RoomConfigRepository roomConfigRepository;
    private final RoomPriceRepository roomPriceRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HouseTypeRepository houseTypeRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    public RoomConfigDto addRoomConfig(RoomConfigDto.CreateRoomConfig roomConfig){
        RoomPriceEntity normalPriceEntity = roomPriceRepository.findByName(roomConfig.getNormalPrice())
                .orElseThrow(() -> new RuntimeException("Normal Room Price is not existed"));

        RoomPriceEntity coldPriceEntity = roomPriceRepository.findByName(roomConfig.getColdPrice())
                .orElseThrow(() -> new RuntimeException("Cold Room Price is not existed"));

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findByName(roomConfig.getRoomType())
                .orElseThrow(() -> new RuntimeException("Room type is not existed"));

        HouseTypeEntity houseTypeEntity = houseTypeRepository.findByName(roomConfig.getHouseType())
                .orElseThrow(() -> new RuntimeException("House type is not existed"));

        String fullName = roomConfig.getName() + "-" + convertToEnglish(houseTypeEntity.getName());

        RoomConfigEntity roomConfigEntity = RoomConfigEntity.builder()
                .name(roomConfig.getName())
                .fullName(fullName)
                .roomType(roomTypeEntity)
                .houseType(houseTypeEntity)
                .normalPrice(normalPriceEntity)
                .coldPrice(coldPriceEntity)
                .build();

        roomConfigEntity = roomConfigRepository.save(roomConfigEntity);


        CustomerEntity customerEntity = CustomerEntity.builder().build();

        // Create Room
        RoomEntity roomEntity = RoomEntity.builder()
                .roomConfig(roomConfigEntity)
                .status(RoomEntity.RoomStatus.AVAILABLE)
                .customer(customerEntity)
                .build();

        customerEntity = customerRepository.save(customerEntity);

        roomRepository.save(roomEntity);



        return convertToDto(roomConfigEntity);
    }

    public RoomConfigDto updateRoomConfig(Long id,
                                          RoomConfigDto.UpdateRoomConfig roomConfig) {
        RoomConfigEntity roomConfigEntity = roomConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomConfig is not existed"));

        if(roomConfig.getName() != null) {
            roomConfigEntity.setName(roomConfig.getName());
        }

        if(roomConfig.getNormalPrice() != null) {
            RoomPriceEntity normalPriceEntity = roomPriceRepository.findByName(roomConfig.getNormalPrice())
                    .orElseThrow(() -> new RuntimeException("Normal Room Price is not existed"));

            roomConfigEntity.setNormalPrice(normalPriceEntity);
        }

        if(roomConfig.getColdPrice() != null) {
            RoomPriceEntity coldPriceEntity = roomPriceRepository.findByName(roomConfig.getColdPrice())
                    .orElseThrow(() -> new RuntimeException("Cold Room Price is not existed"));

            roomConfigEntity.setColdPrice(coldPriceEntity);
        }

        if(roomConfig.getHouseType() != null) {
            HouseTypeEntity houseTypeEntity = houseTypeRepository.findByName(roomConfig.getHouseType())
                    .orElseThrow(() -> new RuntimeException("House type is not existed"));

            roomConfigEntity.setHouseType(houseTypeEntity);
        }

        if(roomConfig.getRoomType() != null) {
            RoomTypeEntity roomTypeEntity = roomTypeRepository.findByName(roomConfig.getRoomType())
                    .orElseThrow(() -> new RuntimeException("Room type is not existed"));

            roomConfigEntity.setRoomType(roomTypeEntity);
        }

        roomConfigRepository.save(roomConfigEntity);

        return convertToDto(roomConfigEntity);
    }

    public void deleteRoomConfig(Long id) {
        RoomConfigEntity roomConfigEntity = roomConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomConfig is not existed"));
        try {
            roomConfigRepository.delete(roomConfigEntity);
        } catch (Exception e) {
            throw new RuntimeException("This configuration is used by some rooms");
        }
    }

    public List<RoomConfigDto> getListRoomConfig() {
        List<RoomConfigEntity> roomConfigEntityList = roomConfigRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        List<RoomConfigDto> roomConfigDtoList = new ArrayList<>();

        roomConfigEntityList.stream().forEach((entity) -> {
            RoomConfigDto dto = convertToDto(entity);
            roomConfigDtoList.add(dto);
        });

        return roomConfigDtoList;
    }

    private RoomConfigDto convertToDto(RoomConfigEntity entity) {
        return RoomConfigDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .normalPrice(entity.getNormalPrice().getName())
                .coldPrice(entity.getColdPrice().getName())
                .houseType(entity.getHouseType().getName())
                .roomType(entity.getRoomType().getName())
                .build();
    }

    public String convertToEnglish(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase()
                    .replaceAll(" ", "-")
                    .replaceAll("Đ", "D")
                    .replaceAll("đ", "d");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
