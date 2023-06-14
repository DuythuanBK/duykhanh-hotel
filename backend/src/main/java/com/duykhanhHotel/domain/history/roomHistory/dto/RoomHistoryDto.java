package com.duykhanhHotel.domain.history.roomHistory.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomHistoryDto {
    private Long id;
    private String name;
    private String houseType;
    private Integer rentingType;
    private LocalDateTime arrivalTime;
    private LocalDateTime leaveTime;
    private Integer roomFee;
    private Integer menuFee;
    private Integer totalFee;
    private Customer customer;
    private List<PurchasedItem> order;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Customer {
        private Long id;
        private String firstName;
        private String lastName;
        private String idNumber;
        private String numberplate;
        private LocalDate dateOfBirth;
        private String address;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class PurchasedItem {
        private Long id;
        private String name;
        private Integer price;
        private Integer quantity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class MultipleRoomHistory {
        private List<RoomHistoryDto> rooms;
        private Integer counts;
    }
}
