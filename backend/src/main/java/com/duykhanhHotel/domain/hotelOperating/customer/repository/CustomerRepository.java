package com.duykhanhHotel.domain.hotelOperating.customer.repository;

import com.duykhanhHotel.domain.hotelOperating.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByRoomId(Long id);

    @Query(value = "SELECT c.* FROM customer c " +
            "LEFT JOIN room r ON r.customer_id = c.id " +
            "LEFT JOIN room_config rc ON rc.id = r.room_config_id " +
            "WHERE rc.full_name = :fullName",
            nativeQuery = true)
    Optional<CustomerEntity> findByRoomFullName(@Param("fullName") String fullName);
}
