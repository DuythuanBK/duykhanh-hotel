package com.duykhanhHotel.domain.history.customerHistory.service;

import com.duykhanhHotel.domain.history.customerHistory.dto.CustomerHistoryDto;
import com.duykhanhHotel.domain.history.customerHistory.entity.CustomerHistoryEntity;
import com.duykhanhHotel.domain.history.customerHistory.repository.CustomerHistoryRepository;
import com.duykhanhHotel.domain.hotelOperating.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerHistoryService {
    private final CustomerHistoryRepository repository;

    public CustomerHistoryDto getCustomerHistoryByRoomId(Long roomId) {
        CustomerHistoryEntity entity = repository.findByRoomHistoryId(roomId)
                .orElseThrow(() -> new RuntimeException("Customer history is not existed"));

        return convertToDto(entity);
    }

    public void updateCustomerHistory(CustomerHistoryDto dto) {
        CustomerHistoryEntity entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Customer history is not existed"));

        if(dto.getFirstName() != null) {
            entity.setAddress(dto.getAddress());
        }

        repository.save(entity);
    }

    private CustomerHistoryDto convertToDto(CustomerHistoryEntity entity) {
        return CustomerHistoryDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .address(entity.getAddress())
                .idNumber(entity.getIdNumber())
                .dateOfBirth(entity.getDateOfBirth())
                .numberplate(entity.getNumberplate())
                .build();

    }

}
