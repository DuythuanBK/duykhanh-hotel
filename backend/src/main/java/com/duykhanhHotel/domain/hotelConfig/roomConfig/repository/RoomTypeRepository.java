package com.duykhanhHotel.domain.hotelConfig.roomConfig.repository;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {

    public Optional<RoomTypeEntity> findByName(String name);
}
