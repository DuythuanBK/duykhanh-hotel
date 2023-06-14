package com.duykhanhHotel.domain.hotelOperating.customer.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@JsonTypeName("customer")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String idNumber;
    private String numberplate;
    private String address;

    @Builder
    @Data
    @JsonTypeName("customer")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class UpdateCustomer {
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String idNumber;
        private String numberplate;
        private String address;
    }

}
