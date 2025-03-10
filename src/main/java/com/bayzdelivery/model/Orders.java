package com.bayzdelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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