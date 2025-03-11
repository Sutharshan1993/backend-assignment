package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations and database interactions
 * for the Orders entity.
 * <p>
 * This interface extends JpaRepository, providing a wide range of methods for
 * retrieving, persisting, updating, and deleting Orders objects.
 * <p>
 * Functionality:
 * - Automatically provides implementation for common JPA methods such as
 * save, findById, findAll, delete, etc.
 * - Extends Spring Data's JpaRepository, which simplifies repository development
 * by reducing boilerplate code.
 * - Designed for integration with the Orders entity.
 * <p>
 * OrdersRepository is annotated with @Repository, making it a component eligible
 * for auto-detection and dependency injection in the Spring context.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
