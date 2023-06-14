package com.duykhanhHotel.domain.hotelOperating.room.repository;

import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query(value = "select r.* from room r " +
            "left join room_config rc on rc.id = r.room_config_id " +
            "left join house_type ht on ht.id = rc.house_type_id " +
            "where ht.name = :houseType",
            nativeQuery = true)
    List<RoomEntity> findByHouseTye(@Param("houseType") String houseType);

    @Query(value = "SELECT r.* from room r " +
            "left join room_config rc on rc.id = r.room_config_id " +
            "where rc.full_name = :fullName",
            nativeQuery = true)
    Optional<RoomEntity> findByRoomFullName(@Param("fullName") String fullName);
}
