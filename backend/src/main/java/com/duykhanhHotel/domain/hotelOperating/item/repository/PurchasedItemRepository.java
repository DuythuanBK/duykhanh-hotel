package com.duykhanhHotel.domain.hotelOperating.item.repository;

import com.duykhanhHotel.domain.hotelOperating.item.entity.PurchasedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasedItemRepository extends JpaRepository<PurchasedItemEntity, Long> {
    @Query("SELECT i FROM PurchasedItemEntity i " +
            "INNER JOIN RoomEntity r ON i.room.id = r.id " +
            "WHERE r.roomConfig.name = :roomName")
    List<PurchasedItemEntity> findByRoomName(@Param("roomName") String roomName);

    @Query("DELETE FROM PurchasedItemEntity i " +
            "WHERE i.room.roomConfig.name = :roomName ")
    void deleteAllItemByRoomName(@Param("roomName") String roomName);

    @Query("SELECT i FROM PurchasedItemEntity i " +
            "INNER JOIN RoomEntity r ON i.room.id = r.id " +
            "WHERE r.id = :id")
    List<PurchasedItemEntity> findByRoomId(@Param("id") Long id);

    @Query("SELECT SUM(i.price * i.quantity) FROM PurchasedItemEntity i " +
            "INNER JOIN RoomEntity r ON i.room.id = r.id " +
            "WHERE r.id = :id")
    Integer calculateMenuFeeByRoomId(@Param("id") Long id);

    @Query("DELETE FROM PurchasedItemEntity i WHERE i.room.id = :id")
    void deleteAllItemByRoomId(@Param("id") Long id);

    @Query(value = "SELECT pi.* from purchased_item pi " +
            "LEFT JOIN room r ON r.id = pi.room_id " +
            "LEFT JOIN room_config rc ON rc.id = r.room_config_id " +
            "WHERE rc.full_name = :fullName",
    nativeQuery = true)
    List<PurchasedItemEntity> findByRoomFullName(@Param("fullName") String fullName);
}
