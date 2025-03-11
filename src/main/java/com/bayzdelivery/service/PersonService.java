package com.bayzdelivery.service;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;

import java.util.List;

/**
 * PersonService interface defines the operations for managing person-related
 * functionalities within the system. It provides methods to handle person
 * registration, retrieval, and querying by ID.
 * <p>
 * Core Features:
 * - Retrieve a list of all registered persons.
 * - Save a new person's details in the system.
 * - Fetch details of a specific person using their unique identifier.
 * <p>
 * This service ensures proper handling of person-related data, playing a crucial
 * role in user management operations.
 */
public interface PersonService {
    List<PersonRegisterResponse> getAll();

    PersonRegisterResponse save(Person p);

    PersonRegisterResponse findById(Long personId);

}
