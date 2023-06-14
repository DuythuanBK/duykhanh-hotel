package com.duykhanhHotel.domain.hotelConfig.roomConfig.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class RoomPriceDto {
    private Long id;
    private String name;
    private Integer firstHour;
    private Integer nextHour;
    private Integer overnight;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("roomPrice")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        @JsonProperty("name")
        private String name;
        @JsonProperty("firstHour")
        private Integer firstHour;
        @JsonProperty("nextHour")
        private Integer nextHour;
        @JsonProperty("overnight")
        private Integer overnight;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class SingleRoomPrice {
        private RoomPriceDto roomPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("roomPrices")
    public static class MultipleRoomPrice {
        private List<RoomPriceDto> roomPrices;
    }
}