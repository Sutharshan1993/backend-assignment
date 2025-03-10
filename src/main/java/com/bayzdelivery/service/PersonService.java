package com.bayzdelivery.service;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;

import java.util.List;

public interface PersonService {
    List<PersonRegisterResponse> getAll();

    PersonRegisterResponse save(Person p);

    PersonRegisterResponse findById(Long personId);

}
