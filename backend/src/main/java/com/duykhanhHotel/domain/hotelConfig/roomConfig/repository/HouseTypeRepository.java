package com.duykhanhHotel.domain.hotelConfig.roomConfig.repository;

import com.duykhanhHotel.domain.hotelConfig.roomConfig.entity.HouseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseTypeRepository extends JpaRepository<HouseTypeEntity, Long> {
    public Optional<HouseTypeEntity> findByName(String name);
}
