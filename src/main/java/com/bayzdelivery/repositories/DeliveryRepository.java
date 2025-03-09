package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
