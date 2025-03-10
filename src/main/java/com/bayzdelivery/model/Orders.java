package com.bayzdelivery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderName;
    private double orderPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Person customer;

    private LocalDateTime orderTime;

}
