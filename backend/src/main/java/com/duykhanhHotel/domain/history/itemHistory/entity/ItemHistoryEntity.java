package com.duykhanhHotel.domain.history.itemHistory.entity;

import com.duykhanhHotel.domain.history.roomHistory.entity.RoomHistoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ITEM_HISTORY")
public class ItemHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "room_history_id")
    private RoomHistoryEntity roomHistory;
}
