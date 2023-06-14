package com.duykhanhHotel.domain.hotelConfig.roomConfig.repository;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.RoomConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomConfigRepository extends JpaRepository<RoomConfigEntity, Long> {

}
