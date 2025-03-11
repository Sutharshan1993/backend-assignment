package com.bayzdelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the Orders entity in the system. This class is used for managing
 * information about customer orders, including order details such as name,
 * price, associated customer, and the order's timestamp.
 * <p>
 * This entity is mapped to the "orders" table in the database using JPA annotations.
 * <p>
 * Key Features:
 * 1. Each order is uniquely identified by an id.
 * 2. Captures mandatory details such as the order name and price.
 * 3. Establishes a relationship between an order and a customer using a
 * many-to-one association.
 * 4. Stores the time when the order was placed.
 * <p>
 * Includes overridden `equals` and `hashCode` methods for comparing and
 * hashing objects based on their properties.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private double orderPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Person customer;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Orders orders = (Orders) o;
        return Double.compare(orders.orderPrice, orderPrice) == 0 &&
                Objects.equals(id, orders.id) &&
                Objects.equals(orderName, orders.orderName) &&
                Objects.equals(customer, orders.customer) &&
                Objects.equals(orderTime, orders.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderName, orderPrice, customer, orderTime);
    }
}