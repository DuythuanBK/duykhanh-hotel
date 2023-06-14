package com.duykhanhHotel.domain.history.roomHistory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomHistoryParam extends FeedParams {
    private String houseType;
    private LocalDate date;
}
