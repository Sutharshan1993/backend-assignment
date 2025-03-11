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

/**
 * Repository interface for managing delivery-related operations in the database.
 * This interface extends JpaRepository to provide basic CRUD and query operations
 * for the Delivery entity.
 * <p>
 * Additional custom queries are provided for specific business requirements, such as:
 * 1. Counting deliveries by delivery man's ID and status.
 * 2. Fetching statistics for delivery persons based on commission earned within a time frame.
 * 3. Retrieving deliveries by status with a start time before a given threshold.
 * <p>
 * Methods:
 * - countByDeliveryManIdAndStatus: Counts the number of deliveries for a specific delivery person and status.
 * - findTopDeliveryMenByCommission: Retrieves the top delivery persons based on total commission earned
 * and allows filtering by a time period.
 * - findByStatusAndStartTimeBefore: Finds deliveries based on a specific status and before a certain start time.
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    /**
     * Counts the number of deliveries associated with a specific delivery person and a given delivery status.
     *
     * @param deliveryManId the ID of the delivery person whose deliveries are to be counted
     * @param status        the status of deliveries to be included in the count
     * @return the number of deliveries for the specified delivery person and status
     */
    int countByDeliveryManIdAndStatus(Long deliveryManId, DeliveryStatus status);

    /**
     * Retrieves the top delivery men based on the total commission earned within a specified date and time range.
     * The result includes each delivery man's ID, name, total commission earned, and the number of completed deliveries in the given period.
     **/
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

    /**
     * Finds a list of deliveries based on the specified status and whose start time is before the given threshold.
     *
     * @param status    the status of the deliveries to be filtered
     * @param threshold the time threshold; deliveries with a start time before this instant will be included
     * @return a list of deliveries matching the specified criteria
     */
    List<Delivery> findByStatusAndStartTimeBefore(
            @Param("status") DeliveryStatus status,
            @Param("threshold") Instant threshold
    );
}
