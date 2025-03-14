package com.bayzdelivery.model;

import com.bayzdelivery.utils.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Represents a delivery entity which stores details about the delivery,
 * including its timings, distance, price, commission, associated delivery man,
 * customer, related order, and current status.
 * <p>
 * This class is mapped to the "delivery" table in the database, and leverages
 * JPA annotations for database operations.
 * <p>
 * Key Features:
 * 1. Identifies a unique delivery using a primary key.
 * 2. Maintains start and end times for the delivery.
 * 3. Tracks the distance covered during the delivery.
 * 4. Captures pricing and commission information.
 * 5. Associates the delivery with a delivery person, a customer, and an order.
 * 6. Monitors the delivery's current progress status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    @Serial
    private static final long serialVersionUID = 123765351514001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "price")
    private double price;

    @Column(name = "commission")
    private double commission;

    @ManyToOne
    @JoinColumn(name = "delivery_man_id", referencedColumnName = "id")
    private Person deliveryMan;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Person customer;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders orders;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Delivery delivery = (Delivery) o;
        return Double.compare(delivery.price, price) == 0 &&
                Double.compare(delivery.commission, commission) == 0 &&
                Objects.equals(id, delivery.id) &&
                Objects.equals(startTime, delivery.startTime) &&
                Objects.equals(endTime, delivery.endTime) &&
                Objects.equals(distance, delivery.distance) &&
                Objects.equals(deliveryMan, delivery.deliveryMan) &&
                Objects.equals(customer, delivery.customer) &&
                Objects.equals(orders, delivery.orders) &&
                status == delivery.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, distance, price, commission, deliveryMan, customer, orders, status);
    }
}