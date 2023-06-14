package com.duykhanhHotel.domain.hotelConfig.roomConfig.entity;

import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "room_config", uniqueConstraints = {@UniqueConstraint(name = "UniqueRoom", columnNames = {"name", "house_type_id"})})
public class RoomConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fullName;

    @ManyToOne
    @JoinColumn(name = "normal_price_id")
    private RoomPriceEntity normalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cold_price_id")
    private RoomPriceEntity coldPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomTypeEntity roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_type_id")
    private HouseTypeEntity houseType;

    @OneToOne(mappedBy = "roomConfig", cascade = CascadeType.REMOVE)
    private RoomEntity room;

}
