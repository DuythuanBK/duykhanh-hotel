package com.duykhanhHotel.domain.hotelConfig.item.resipotory;

import com.duykhanhHotel.domain.hotelConfig.item.entity.ItemTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemTypeEntity, Long> {
    Optional<ItemTypeEntity> findByName(String name);
}
