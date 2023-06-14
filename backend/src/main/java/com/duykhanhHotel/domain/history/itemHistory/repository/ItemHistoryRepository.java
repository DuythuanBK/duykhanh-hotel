package com.duykhanhHotel.domain.history.itemHistory.repository;

import com.duykhanhHotel.domain.history.itemHistory.dto.ItemHistoryDto;
import com.duykhanhHotel.domain.history.itemHistory.entity.ItemHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemHistoryRepository extends JpaRepository<ItemHistoryEntity, Long> {
    List<ItemHistoryEntity> findByRoomHistoryId(Long roomId);
}
