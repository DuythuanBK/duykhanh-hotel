package com.duykhanhHotel.domain.history.roomHistory.repository;

import com.duykhanhHotel.domain.history.roomHistory.entity.RoomHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomHistoryRepository extends JpaRepository<RoomHistoryEntity, Long> {
    @Query(value = "SELECT r.* FROM room_history r " +
            "WHERE r.house_type = :houseType",
            nativeQuery = true)
    List<RoomHistoryEntity> findByNameAndHouseType(@Param("houseType") String houseType);

    @Query(value = "SELECT r.* FROM room_history r " +
            "WHERE r.house_type = :houseType " +
            "ORDER BY r.arrival_time",
            nativeQuery = true)
    Page<RoomHistoryEntity> findByHouseType(@Param("houseType") String houseType, Pageable pageable);

    @Query(value = "SELECT r.* FROM room_history r " +
            "WHERE r.house_type = :houseType " +
            "AND DATE(r.arrival_time) = :date " +
            "ORDER BY r.arrival_time",
            nativeQuery = true)
    Page<RoomHistoryEntity> findByHouseTypeAndOnDate(@Param("houseType") String houseType,
                                                     @Param("date") LocalDate date,
                                                     Pageable pageable);

    @Query(value = "SELECT r.* FROM room_history r " +
            "WHERE r.house_type = :houseType " +
            "AND r.arrival_time >= :fromDate AND r.arrival_time <= :endDate",
            nativeQuery = true)
    List<RoomHistoryEntity> findByHouseTypeAndDateRange(@Param("houseType") String houseType,
                                                   @Param("fromDate") LocalDate fromDate,
                                                   @Param("endDate") LocalDate endDate);
}
