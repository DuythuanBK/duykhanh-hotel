package com.duykhanhHotel.domain.hotelConfig.roomConfig.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@Data
@Builder
public class RoomConfigDto {
    private Long id;
    private String name;

    private String normalPrice;
    private String coldPrice;
    private String roomType;
    private String houseType;
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class SingleRoomConfig {
        private RoomConfigDto roomConfig;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("roomConfig")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class CreateRoomConfig {
        private String name;
        private String normalPrice;
        private String coldPrice;
        private String roomType;
        private String houseType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("roomConfig")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class UpdateRoomConfig {
        private String name;
        private String normalPrice;
        private String coldPrice;
        private String roomType;
        private String houseType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("roomConfigs")
    public static class MultipleRoomConfig {
        private List<RoomConfigDto> roomConfigs;
    }

}
