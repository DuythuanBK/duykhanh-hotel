package com.duykhanhHotel.domain.history.customerHistory.entity;

import com.duykhanhHotel.domain.history.roomHistory.entity.RoomHistoryEntity;
import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "CUSTOMER_HISTORY")
public class CustomerHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String numberplate;
    private String idNumber;

    @OneToOne(mappedBy = "customerHistory")
    private RoomHistoryEntity roomHistory;
}
