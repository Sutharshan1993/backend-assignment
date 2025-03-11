package com.bayzdelivery.service;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.exceptions.PersonNotFoundException;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.PersonHelper;
import com.bayzdelivery.utils.PersonRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;

    @Override
    public List<PersonRegisterResponse> getAll() {
        log.info("Entered into getAll Users");
        return personRepository.findAll()
                .stream()
                .map(PersonHelper::mapRegistedPerson)
                .toList();
    }

    public PersonRegisterResponse save(Person user) {
        log.info("Entered into save Users");
        if ( !PersonRole.CUSTOMER.equals(user.getRole()) && !PersonRole.DELIVERY_MAN.equals(user.getRole()) ) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or DELIVERY_MAN");
        }
        Person personReg = personRepository.save(user);
        log.info("Exiting from save Users");
        return new PersonRegisterResponse(personReg.getId(), personReg.getName(), personReg.getRegistrationNumber(), personReg.getRole());
    }

    @Override
    public PersonRegisterResponse findById(Long userId) {
        log.info("Entered into findById Users of User Id :{}", userId);
        return personRepository.findById(userId).map(userDtl -> {
            log.info("Found user and Exiting: {}", userDtl);
            return PersonHelper.mapRegistedPerson(userDtl);
        }).orElseThrow(() -> new PersonNotFoundException("Person not found with ID: " + userId));

    }
}
