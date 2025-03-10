package com.bayzdelivery.repositories;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.utils.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.deliveryMan.id = :deliveryManId AND d.status = 'ACTIVE'")
    int countActiveDeliveriesByDeliveryManId(@Param("deliveryManId") Long deliveryManId);

    @Query(""" 
                    SELECT d.deliveryMan.id,
                           d.deliveryMan.name,
                           SUM(d.commission),
                           COUNT(d.id)
                       FROM Delivery d
                       WHERE d.startTime BETWEEN :startTime AND :endTime
                       AND d.status = 'COMPLETED'
                       GROUP BY d.deliveryMan.id, d.deliveryMan.name
                       ORDER BY SUM(d.commission) DESC""")
    List<Object[]> findTopDeliveryMenByCommission(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT d FROM Delivery d WHERE d.status = :status AND d.startTime < :threshold")
    List<Delivery> findByStatusAndStartTimeBefore(
            @Param("status") DeliveryStatus status,
            @Param("threshold") Instant threshold
    );
}
