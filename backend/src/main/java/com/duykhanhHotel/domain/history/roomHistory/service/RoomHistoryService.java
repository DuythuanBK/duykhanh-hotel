package com.duykhanhHotel.domain.history.roomHistory.service;

import com.duykhanhHotel.domain.history.customerHistory.entity.CustomerHistoryEntity;
import com.duykhanhHotel.domain.history.customerHistory.repository.CustomerHistoryRepository;
import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import com.duykhanhHotel.domain.history.itemHistory.repository.ItemHistoryRepository;
import com.duykhanhHotel.domain.history.roomHistory.dto.RoomHistoryDto;
import com.duykhanhHotel.domain.history.roomHistory.entity.RoomHistoryEntity;
import com.duykhanhHotel.domain.history.roomHistory.model.RoomHistoryParam;
import com.duykhanhHotel.domain.history.roomHistory.repository.RoomHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomHistoryService {
    private final RoomHistoryRepository roomHistoryRepository;
    private final ItemHistoryRepository itemHistoryRepository;
    private final CustomerHistoryRepository customerHistoryRepository;

    public RoomHistoryDto.MultipleRoomHistory getRoomHistoryList(RoomHistoryParam param) {

        List<RoomHistoryEntity> roomHistoryEntityList = new ArrayList<>();
        Page<RoomHistoryEntity> page = Page.empty();
        Pageable pageable = PageRequest.of(param.getOffset(), param.getLimit());

        if (param.getHouseType() != null && param.getDate() != null) {
            page = roomHistoryRepository.findByHouseTypeAndOnDate( param.getHouseType(),
                                                                    param.getDate(),
                                                                    pageable);
        } else {
            page = roomHistoryRepository.findByHouseType(param.getHouseType(), pageable);
        }
        roomHistoryEntityList = page.getContent();

        List<RoomHistoryDto> dtos = new ArrayList<>();

        roomHistoryEntityList.stream().forEach((entity) -> {
            RoomHistoryDto dto = convertRoomHistoryEntityToDto(entity);
            dtos.add(dto);
        });

        return RoomHistoryDto.MultipleRoomHistory.builder()
                .rooms(dtos)
                .counts(page.getTotalPages())
                .build();
    }

    private RoomHistoryDto convertRoomHistoryEntityToDto(RoomHistoryEntity entity) {

        RoomHistoryDto.Customer customer = covertCustomerHistoryToDto(entity.getCustomerHistory());

        List<RoomHistoryDto.PurchasedItem> orders = new ArrayList<>();

        entity.getItemHistoryList().stream().forEach(item -> {
            orders.add(convertPurchasedItemToDto(item));
        });

        RoomHistoryDto dto =  RoomHistoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .arrivalTime(entity.getArrivalTime())
                .leaveTime(entity.getLeaveTime())
                .menuFee(entity.getMenuFee())
                .roomFee(entity.getRoomFee())
                .totalFee(entity.getTotalFee())
                .houseType(entity.getHouseType())
                .rentingType(entity.getRentingType().ordinal())
                .customer(customer)
                .order(orders)
                .build();

        return dto;
    }


    private RoomHistoryDto.Customer covertCustomerHistoryToDto(CustomerHistoryEntity entity) {
        return RoomHistoryDto.Customer.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .address(entity.getAddress())
                .numberplate(entity.getNumberplate())
                .dateOfBirth(entity.getDateOfBirth())
                .idNumber(entity.getIdNumber())
                .build();
    }

    private RoomHistoryDto.PurchasedItem convertPurchasedItemToDto(ItemHistoryEntity entity) {
        return RoomHistoryDto.PurchasedItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
