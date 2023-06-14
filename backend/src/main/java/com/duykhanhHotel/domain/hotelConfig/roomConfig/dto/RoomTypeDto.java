package com.duykhanhHotel.domain.hotelConfig.roomConfig.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
public class RoomTypeDto {
    private Long id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonTypeName("roomType")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        private String name;
    }

    @Data
    @Builder
    public static class SingleRoomType {
        private RoomTypeDto roomType;
    }

    @Data
    @Builder
    @JsonTypeName("roomTypes")
    public static class MultipleRoomType {
        private List<RoomTypeDto> roomTypes;
    }
}
