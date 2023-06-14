package com.duykhanhHotel.domain.hotelOperating.room.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RoomDto {
    public enum RoomStatus {
        AVAILABLE,
        OCCUPIED,
        CLEANING
    }

    public enum RentingType {
        HOUR,
        OVERNIGHT,
        MONTHLY
    }

    private Long id;
    private String name;
    private String fullName;
    private LocalDateTime arrivalTime;
    private LocalDateTime leaveTime;
    private RoomStatus status;
    private String houseType;

    @Builder.Default
    private RentingType rentingType = RentingType.HOUR;

    @Builder.Default
    private int roomFee = 0;
    @Builder.Default
    private int menuFee = 0;
    @Builder.Default
    private int deposit = 0;
    @Builder.Default
    private int totalFee = 0;
    @Builder.Default
    private boolean usingAirConditioner = false;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @JsonTypeName("room")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class UpdateRoom {
        private LocalDateTime arrivalTime;
        private RoomStatus status;
        private Integer deposit;
        private Boolean usingAirConditioner;
        private RentingType rentingType;
    }

    @Builder
    @Data
    @JsonTypeName("room")
    public static class SingleRoom {
        private RoomDto room;
    }

    @Builder
    @Data
    @JsonTypeName("rooms")
    public static class MultipleRoom {
        private List<RoomDto> rooms;
    }

}
