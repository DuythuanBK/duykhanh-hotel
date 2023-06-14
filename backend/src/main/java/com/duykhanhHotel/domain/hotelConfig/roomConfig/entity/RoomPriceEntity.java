package com.duykhanhHotel.domain.hotelConfig.roomConfig.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ROOM_PRICE")
public class RoomPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer firstHour;
    private Integer nextHour;
    private Integer overnight;

    @OneToMany(mappedBy = "normalPrice")
    private List<RoomConfigEntity> normalRoomConfigs;

    @OneToMany(mappedBy = "coldPrice")
    private List<RoomConfigEntity> coldRoomConfigs;
}
