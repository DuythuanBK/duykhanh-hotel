package com.duykhanhHotel.domain.hotelConfig.roomConfig.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@Data
@Builder
public class HouseTypeDto {
    private Long id;
    private String name;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    @JsonTypeName("houseType")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class SingleHouseType {
        private HouseTypeDto houseType;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonTypeName("houseTypes")
    public static class MultipleHouseType {
        private List<HouseTypeDto> houseTypes;
    }
}
