package com.bayzdelivery.repositories;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;


@RepositoryRestResource(exported = false)
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
}
