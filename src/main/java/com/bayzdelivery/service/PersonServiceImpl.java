package com.bayzdelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.exceptions.DeliveryNotFoundException;
import com.bayzdelivery.exceptions.PersonNotFoundException;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.utils.PersonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<PersonRegisterResponse> getAll() {
        return personRepository.findAll()
                .stream()
                .map(PersonHelper::mapToResponse)
                .toList();
    }

    public PersonRegisterResponse save(Person person) {
        Person personReg;
        if (!person.getRole().equals("CUSTOMER") && !person.getRole().equals("DELIVERY_MAN")) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or DELIVERY_MAN");
        }
        personReg= personRepository.save(person);
        return new PersonRegisterResponse(personReg.getId(),personReg.getName(),personReg.getRegistrationNumber(),personReg.getRole());
    }

    @Override
    public PersonRegisterResponse findById(Long personId) {
        return  personRepository.findById(personId).map(PersonHelper::mapRegistedPerson).orElseThrow(() -> new PersonNotFoundException("Person not found with ID: " + personId));

    }
}
