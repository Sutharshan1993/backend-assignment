package com.bayzdelivery.utils;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;

/**
 * Utility class for handling person-related data transformations.
 * <p>
 * This class provides static utility methods to map and transform
 * `Person` entities to their corresponding DTOs, such as `PersonRegisterResponse`.
 * It is designed to be non-instantiable and should only be used for its static methods.
 */
public class PersonHelper {
    private PersonHelper() {
    }

    public static PersonRegisterResponse mapRegistedPerson(Person person) {
        return new PersonRegisterResponse(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getRegistrationNumber(),
                person.getRole()
        );
    }

}
