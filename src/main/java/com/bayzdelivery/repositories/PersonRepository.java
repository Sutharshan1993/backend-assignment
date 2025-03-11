package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Person entities in the database.
 * <p>
 * This interface extends JpaRepository, which provides various built-in methods
 * for performing standard CRUD operations and simplifies JPA-based repositories.
 * <p>
 * Functionality:
 * - Automatically provides implementation for JPA methods such as save, delete,
 * findById, findAll, etc.
 * - Designed specifically for the Person entity with a primary key of type Long.
 * - Enhances integration with the Person entity while reducing boilerplate code.
 * <p>
 * PersonRepository is annotated with @Repository, making it eligible for Spring's
 * component scanning and dependency injection.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
