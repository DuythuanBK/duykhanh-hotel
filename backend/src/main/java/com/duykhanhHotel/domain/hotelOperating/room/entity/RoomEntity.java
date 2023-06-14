package com.duykhanhHotel.domain.hotelOperating.room.entity;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomConfigEntity;
import com.duykhanhHotel.domain.hotelOperating.customer.entity.CustomerEntity;
import com.duykhanhHotel.domain.hotelOperating.item.entity.PurchasedItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@NamedEntityGraph(name = "fetch-roomconfig", attributeNodes = {@NamedAttributeNode("roomConfig")})
@Table(name = "ROOM")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "roomConfig_id", nullable = false)
    private RoomConfigEntity roomConfig;

    private LocalDateTime arrivalTime;

    private LocalDateTime leaveTime;

    @Builder.Default
    private RoomStatus status = RoomStatus.AVAILABLE;

    @Builder.Default
    private RentingType rentingType = RentingType.HOUR;

    private int advancePayment;

    @Builder.Default
    private boolean usingAirConditioner = false;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PurchasedItemEntity> purchasedItems;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
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
}
