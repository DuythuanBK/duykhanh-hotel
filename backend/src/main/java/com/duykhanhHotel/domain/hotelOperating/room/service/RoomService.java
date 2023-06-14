package com.duykhanhHotel.domain.hotelOperating.room.service;

import com.duykhanhHotel.domain.history.customerHistory.entity.CustomerHistoryEntity;
import com.duykhanhHotel.domain.history.customerHistory.repository.CustomerHistoryRepository;
import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import com.duykhanhHotel.domain.history.itemHistory.repository.ItemHistoryRepository;
import com.duykhanhHotel.domain.history.roomHistory.entity.RoomHistoryEntity;
import com.duykhanhHotel.domain.history.roomHistory.repository.RoomHistoryRepository;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomPriceEntity;
import com.duykhanhHotel.domain.hotelOperating.customer.entity.CustomerEntity;
import com.duykhanhHotel.domain.hotelOperating.customer.repository.CustomerRepository;
import com.duykhanhHotel.domain.hotelOperating.item.entity.PurchasedItemEntity;
import com.duykhanhHotel.domain.hotelOperating.item.repository.PurchasedItemRepository;
import com.duykhanhHotel.domain.hotelOperating.room.dto.RoomDto;
import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import com.duykhanhHotel.domain.hotelOperating.room.model.RoomQueryParam;
import com.duykhanhHotel.domain.hotelOperating.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemHistoryRepository itemHistoryRepository;
    private final CustomerRepository customerRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final PurchasedItemRepository purchasedItemRepository;

    public List<RoomDto> getListRoom(RoomQueryParam param) {
        List<RoomEntity> roomEntityList;

        if(param.getHouseType() != null) {
            roomEntityList = roomRepository.findByHouseTye(param.getHouseType());
        } else {
            roomEntityList = roomRepository.findAll(Sort.by(Sort.Direction.ASC, "roomConfig.name"));
        }
        List<RoomDto> roomDtoList = new ArrayList<>();

        roomEntityList.stream().forEach((entity) -> {
            RoomDto dto = convertToRoomDto(entity);

            roomDtoList.add(dto);
        });

        return roomDtoList;
    }

    public RoomDto getRoom(String fullName) {
        RoomEntity entity = roomRepository.findByRoomFullName(fullName).orElseThrow(
                () -> new RuntimeException("Room is not existed")
        );

        return convertToRoomDto(entity);
    }

    public RoomDto checkIn(String fullName) {
        RoomEntity entity = roomRepository.findByRoomFullName(fullName).orElseThrow(
                () -> new RuntimeException("Room is not existed")
        );

        entity.setArrivalTime(LocalDateTime.now());
        entity.setStatus(RoomEntity.RoomStatus.OCCUPIED);

        entity = roomRepository.save(entity);

        return convertToRoomDto(entity);
    }

    public RoomDto checkOut(String fullName) {
        RoomEntity roomEntity = roomRepository.findByRoomFullName(fullName).orElseThrow(
                () -> new RuntimeException("Room is not existed")
        );

        List<PurchasedItemEntity> purchasedItemEntityList = purchasedItemRepository.findByRoomId(roomEntity.getId());

        Integer menuFee = 0;
        for(PurchasedItemEntity entity : purchasedItemEntityList) {
            menuFee += entity.getQuantity() * entity.getPrice();
        }

        Integer roomFee = calculateRoomFee(roomEntity);

        CustomerEntity customerEntity = customerRepository.findByRoomId(roomEntity.getId()).get();

        // save customer to history
        CustomerHistoryEntity customerHistory = CustomerHistoryEntity.builder()
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .address(customerEntity.getAddress())
                .numberplate(customerEntity.getNumberplate())
                .dateOfBirth(customerEntity.getDateOfBirth())
                .idNumber(customerEntity.getIdNumber())
                .build();

        customerHistoryRepository.save(customerHistory);

        RoomHistoryEntity roomHistoryEntity = RoomHistoryEntity.builder()
                .name(roomEntity.getRoomConfig().getName())
                .menuFee(menuFee)
                .roomFee(roomFee)
                .totalFee(menuFee + roomFee)
                .arrivalTime(roomEntity.getArrivalTime())
                .leaveTime(LocalDateTime.now())
                .customerHistory(customerHistory)
                .houseType(roomEntity.getRoomConfig().getHouseType().getName())
                .rentingType(RoomHistoryEntity.RentingType.valueOf(roomEntity.getRentingType().toString()))
                .build();

        // save room to history
        RoomHistoryEntity savedRoomHistoryEntity = roomHistoryRepository.save(roomHistoryEntity);

        // save purchased item to history
        for(PurchasedItemEntity purchasedEntity : purchasedItemEntityList) {
            ItemHistoryEntity historyEntity = ItemHistoryEntity.builder()
                            .name(purchasedEntity.getName())
                            .price(purchasedEntity.getPrice())
                            .quantity(purchasedEntity.getQuantity())
                            .roomHistory(savedRoomHistoryEntity)
                            .build();

            itemHistoryRepository.save(historyEntity);
        };

        // reset room
        roomEntity.setStatus(RoomEntity.RoomStatus.CLEANING);
        roomEntity.setArrivalTime(null);
        roomEntity.setUsingAirConditioner(false);

        // delete purchased item
        purchasedItemEntityList.stream().forEach((entity) -> purchasedItemRepository.delete(entity));

        // reset Customer
        customerEntity.setAddress(null);
        customerEntity.setFirstName(null);
        customerEntity.setLastName(null);
        customerEntity.setNumberplate(null);
        customerEntity.setIdNumber(null);
        customerEntity.setDateOfBirth(null);
        customerRepository.save(customerEntity);

        return convertToRoomDto(roomEntity);
    }

    private RoomDto convertToRoomDto(RoomEntity entity) {
        RoomDto dto = RoomDto.builder()
                .id(entity.getId())
                .name(entity.getRoomConfig().getName())
                .fullName(entity.getRoomConfig().getFullName())
                .arrivalTime(entity.getArrivalTime())
                .leaveTime(entity.getLeaveTime())
                .rentingType(RoomDto.RentingType.valueOf(entity.getRentingType().name()))
                .deposit(entity.getAdvancePayment())
                .status(RoomDto.RoomStatus.valueOf(entity.getStatus().name()))
                .usingAirConditioner(entity.isUsingAirConditioner())
                .houseType(entity.getRoomConfig().getHouseType().getName())
                .build();

        if(entity.getStatus() == RoomEntity.RoomStatus.OCCUPIED) {
            Integer menuFee = purchasedItemRepository.calculateMenuFeeByRoomId(entity.getId());
            if(menuFee == null) {
                menuFee = 0;
            }
            Integer roomFee = calculateRoomFee(entity);
            Integer totalFee = menuFee + roomFee;
            dto.setRoomFee(roomFee);
            dto.setMenuFee(menuFee);
            dto.setTotalFee(totalFee);
        }

        return dto;
    }

    public RoomDto updateRoom(String fullName, RoomDto.UpdateRoom dto) {
        RoomEntity entity = roomRepository.findByRoomFullName(fullName).orElseThrow(
                () -> new RuntimeException("Room is not existed"));

        if(dto.getArrivalTime() != null) {
            entity.setArrivalTime(dto.getArrivalTime());
        }

//        if(dto.getLeaveTime() != null) {
//            entity.setLeaveTime(dto.getLeaveTime());
//        }

        if(dto.getStatus() != null) {
            entity.setStatus(RoomEntity.RoomStatus.valueOf(dto.getStatus().toString()));
        }

        if(dto.getDeposit() != null) {
            entity.setAdvancePayment(dto.getDeposit());
        }

        if(dto.getUsingAirConditioner() != null) {
            entity.setUsingAirConditioner(dto.getUsingAirConditioner());
        }

        if(dto.getRentingType() != null) {
            entity.setRentingType(RoomEntity.RentingType.valueOf(dto.getRentingType().name()));
        }

        roomRepository.save(entity);

        return convertToRoomDto(entity);
    }

    private Integer calculateRoomFee(RoomEntity roomEntity) {
        RoomPriceEntity roomPriceEntity = roomEntity.isUsingAirConditioner()
                ? roomEntity.getRoomConfig().getColdPrice() : roomEntity.getRoomConfig().getNormalPrice();
        LocalDateTime arrivalTime = roomEntity.getArrivalTime();
        LocalDateTime leaveTime = LocalDateTime.now();
        if(roomEntity.getRentingType() == RoomEntity.RentingType.HOUR) { // temporary only calculate for hours type
            Duration duration = Duration.between(arrivalTime, leaveTime);
            Long totalMinutes = duration.toMinutes();
            Integer hour = Integer.valueOf(String.valueOf(totalMinutes / 60));
            Integer minute = Integer.valueOf(String.valueOf(totalMinutes % 60));
            if(minute > 15) {
                hour = hour + 1;
            }
            Integer fee = hour > 1 ?
                    Math.toIntExact(roomPriceEntity.getFirstHour() + (hour - 1) * roomPriceEntity.getNextHour()) :
                    Math.toIntExact(roomPriceEntity.getFirstHour());
            return fee;
        } else if(roomEntity.getRentingType() == RoomEntity.RentingType.OVERNIGHT) {
            Integer fee = roomPriceEntity.getOvernight();
            LocalDate nextDate = LocalDate.of(arrivalTime.getYear(), arrivalTime.getMonth(), arrivalTime.getDayOfMonth());
            LocalTime eightAm = LocalTime.of(8, 0);
            LocalDateTime endOvernightTime = LocalDateTime.of(nextDate.plusDays(1), eightAm);
            if(leaveTime.compareTo(endOvernightTime) > 0) {
                Duration duration = Duration.between(arrivalTime, leaveTime);
                Long totalMinutes = duration.toMinutes();
                Integer hour = Integer.valueOf(String.valueOf(totalMinutes / 60));
                Integer minute = Integer.valueOf(String.valueOf(totalMinutes % 60));
                if(minute > 15) {
                    hour = hour + 1;
                }
                fee = hour > 1 ? Math.toIntExact(roomPriceEntity.getOvernight() + (hour - 1) * roomPriceEntity.getNextHour()) :
                        Math.toIntExact(roomPriceEntity.getOvernight());
            }
            return fee;
        } else {

        }

        return 0;
    }

    private Integer roundingHours(Long hours) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(hours));
        int intHours = bigDecimal.intValue();
        Long decimalPart = hours - bigDecimal.intValue();

        Integer minute = Math.toIntExact(decimalPart * 60);
        if(minute > 15) {
            intHours += 1;
        }

        if(intHours == 0) {
            intHours += 1;
        }

        return intHours;
    }


}
