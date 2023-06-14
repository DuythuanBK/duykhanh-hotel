package com.duykhanhHotel.domain.history.roomHistory.entity;

import com.duykhanhHotel.domain.history.customerHistory.entity.CustomerHistoryEntity;
import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.HouseTypeEntity;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ROOM_HISTORY")
public class RoomHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime arrivalTime;
    private LocalDateTime leaveTime;
    private String houseType;
    private RentingType rentingType;
    private Integer roomFee;
    private Integer menuFee;
    private Integer totalFee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_history_id")
    private CustomerHistoryEntity customerHistory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomHistory")
    private List<ItemHistoryEntity> itemHistoryList;

    public enum RentingType {
        HOUR,
        OVERNIGHT
    }
}
