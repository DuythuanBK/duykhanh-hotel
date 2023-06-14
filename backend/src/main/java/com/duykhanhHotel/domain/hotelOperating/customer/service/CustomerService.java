package com.duykhanhHotel.domain.hotelOperating.customer.service;

import com.duykhanhHotel.domain.hotelOperating.customer.dto.CustomerDto;
import com.duykhanhHotel.domain.hotelOperating.customer.entity.CustomerEntity;
import com.duykhanhHotel.domain.hotelOperating.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository repository;

    public void createCustomer(CustomerDto dto) {
        CustomerEntity entity = CustomerEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .idNumber(dto.getIdNumber())
                .numberplate(dto.getNumberplate())
                .build();

        repository.save(entity);
    }

    public CustomerDto updateCustomer(String fullName, CustomerDto dto) {
        CustomerEntity customerEntity = repository.findByRoomFullName(fullName)
                .orElseThrow(() -> new RuntimeException("Customer is not found"));

        if(dto.getFirstName() != null) {
            customerEntity.setFirstName(dto.getFirstName());
        }

        if(dto.getLastName() != null) {
            customerEntity.setLastName(dto.getLastName());
        }

        if(dto.getAddress() != null) {
            customerEntity.setAddress(dto.getAddress());
        }

        if(dto.getIdNumber() != null) {
            customerEntity.setIdNumber(dto.getIdNumber());
        }

        if(dto.getNumberplate() != null) {
            customerEntity.setNumberplate(dto.getNumberplate());
        }

        if(dto.getDateOfBirth() != null) {
            customerEntity.setDateOfBirth(dto.getDateOfBirth());
        }

        customerEntity = repository.save(customerEntity);

        return convertToCustomerDto(customerEntity);

    }

    public void deleteCustomer(CustomerDto dto) {

    }

    public CustomerDto getCustomer(String fullName) {
        CustomerEntity customerEntity = repository.findByRoomFullName(fullName)
                .orElseThrow(() -> new RuntimeException("Customer is not found"));
        return convertToCustomerDto(customerEntity);
    }

    private CustomerDto convertToCustomerDto(CustomerEntity entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .numberplate(entity.getNumberplate())
                .idNumber(entity.getIdNumber())
                .address(entity.getAddress())
                .dateOfBirth(entity.getDateOfBirth())
                .build();
    }
}
