package com.duykhanhHotel.domain.hotelConfig.roomConfig.repository;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomPriceRepository extends JpaRepository<RoomPriceEntity, Long> {
    Optional<RoomPriceEntity> findByName(String name);
}
