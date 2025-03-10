package com.bayzdelivery.repositories;

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

    int countByDeliveryManIdAndStatus(Long deliveryManId, DeliveryStatus status);

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


    List<Delivery> findByStatusAndStartTimeBefore(
            @Param("status") DeliveryStatus status,
            @Param("threshold") Instant threshold
    );
}
