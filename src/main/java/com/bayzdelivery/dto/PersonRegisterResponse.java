package com.bayzdelivery.dto;


import com.bayzdelivery.utils.PersonRole;
import jakarta.validation.constraints.NotNull;

/**
 * Represents the response provided upon successfully registering a person.
 * <p>
 * This record encapsulates the key details related to a registered person, including:
 * - id: Unique identifier for the registered person.
 * - name: Full name of the registered person.
 * - email: Email address of the registered person (may be optional or null).
 * - registrationNumber: A unique number assigned to the person during registration.
 * - role: The role of the person being registered (e.g., CUSTOMER, DELIVERY_MAN, ADMIN).
 * <p>
 * Constructors:
 * - Full constructor for initializing all fields with specific values.
 * - Constructor initializing mandatory registration details including id, name,
 * registrationNumber, and role while keeping email as null.
 * Validation is applied to ensure the role is required.
 */
public record PersonRegisterResponse(Long id, String name, String email, String registrationNumber, PersonRole role) {
    public PersonRegisterResponse(Long id, String name, String registrationNumber, @NotNull(message = "Role is required") PersonRole role) {
        this(id, name, null, registrationNumber, role);
    }
}
