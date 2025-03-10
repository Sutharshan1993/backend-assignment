package com.bayzdelivery.service;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.exceptions.PersonNotFoundException;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.PersonHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;

    @Override
    public List<PersonRegisterResponse> getAll() {
        return personRepository.findAll()
                .stream()
                .map(PersonHelper::mapRegistedPerson)
                .toList();
    }

    public PersonRegisterResponse save(Person person) {
        Person personReg;
        if ( !"CUSTOMER".equals(person.getRole()) && !"DELIVERY_MAN".equals(person.getRole()) ) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or DELIVERY_MAN");
        }
        personReg = personRepository.save(person);
        return new PersonRegisterResponse(personReg.getId(), personReg.getName(), personReg.getRegistrationNumber(), personReg.getRole());
    }

    @Override
    public PersonRegisterResponse findById(Long personId) {
        return personRepository.findById(personId).map(PersonHelper::mapRegistedPerson).orElseThrow(() -> new PersonNotFoundException("Person not found with ID: " + personId));

    }
}
