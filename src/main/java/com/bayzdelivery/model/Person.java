package com.bayzdelivery.model;

import com.bayzdelivery.utils.PersonRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a person entity in the system with attributes such as name, email,
 * registration number, and a specific role. This class is meant to manage user data
 * and is used in various associations within the system.
 * <p>
 * This class is mapped to the "person" table in the database using JPA annotations.
 * <p>
 * Key Features:
 * 1. Each person is uniquely identified by an ID.
 * 2. Manages mandatory properties including name, email, and role, ensuring input
 * validation for these fields.
 * 3. Supports enumeration for the role to denote the person's function within the
 * system (e.g., CUSTOMER, DELIVERY_MAN, ADMIN).
 * 4. Includes overridden methods for `equals` and `hashCode` for object comparison
 * and hashing based on key attributes.
 * <p>
 * Implements the Serializable interface to allow for object serialization and
 * deserialization.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 432154291451321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull
    @Email
    private String email;

    private String registrationNumber;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(email, person.email) &&
                Objects.equals(name, person.name) &&
                Objects.equals(registrationNumber, person.registrationNumber) &&
                role == person.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, registrationNumber, role);
    }
}
