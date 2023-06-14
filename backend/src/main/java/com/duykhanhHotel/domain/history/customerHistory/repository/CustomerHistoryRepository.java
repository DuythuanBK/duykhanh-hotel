package com.duykhanhHotel.domain.history.customerHistory.repository;

import com.duykhanhHotel.domain.history.customerHistory.entity.CustomerHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerHistoryRepository extends JpaRepository<CustomerHistoryEntity, Long> {
    Optional<CustomerHistoryEntity> findByRoomHistoryId(Long roomId);
}
