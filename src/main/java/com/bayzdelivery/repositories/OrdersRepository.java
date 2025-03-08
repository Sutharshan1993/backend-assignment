package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
