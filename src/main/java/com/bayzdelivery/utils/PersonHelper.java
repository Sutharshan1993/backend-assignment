package com.bayzdelivery.utils;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;

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
