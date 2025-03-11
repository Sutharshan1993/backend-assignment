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

/**
 * Implementation of the PersonService interface responsible for managing person-related
 * operations such as retrieval, saving, and querying of person data in the system.
 * <p>
 * This class integrates with the PersonRepository to perform database operations and
 * utilizes helper
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;

    /**
     * Retrieves a list of all registered persons in the system.
     *
     * @return a list of {@code PersonRegisterResponse} objects representing the
     * details of each registered person.
     */
    @Override
    public List<PersonRegisterResponse> getAll() {
        log.info("Entered into getAll Users");
        return personRepository.findAll()
                .stream()
                .map(PersonHelper::mapRegistedPerson)
                .toList();
    }

    /**
     * Saves a person in the repository and returns the corresponding registration response.
     *
     * @param user The person object to be saved. The role of the person must be either
     *             CUSTOMER or DELIVERY_MAN; otherwise, an {@code IllegalArgumentException}
     *             will be thrown.
     * @return A {@code PersonRegisterResponse} containing the
     */
    public PersonRegisterResponse save(Person user) {
        log.info("Entered into save Users");
        if ( !PersonRole.CUSTOMER.equals(user.getRole()) && !PersonRole.DELIVERY_MAN.equals(user.getRole()) ) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or DELIVERY_MAN");
        }
        Person personReg = personRepository.save(user);
        log.info("Exiting from save Users");
        return new PersonRegisterResponse(personReg.getId(), personReg.getName(), personReg.getRegistrationNumber(), personReg.getRole());
    }

    /**
     * Retrieves a person's registration details based on their unique identifier.
     *
     * @param userId The unique identifier of the person to be retrieved.
     * @return A {@code PersonRegisterResponse} containing the registration details
     * of the person if found.
     * @throws PersonNotFoundException If no person with the given ID is found.
     */
    @Override
    public PersonRegisterResponse findById(Long userId) {
        log.info("Entered into findById Users of User Id :{}", userId);
        return personRepository.findById(userId).map(userDtl -> {
            log.info("Found user and Exiting: {}", userDtl);
            return PersonHelper.mapRegistedPerson(userDtl);
        }).orElseThrow(() -> new PersonNotFoundException("Person not found with ID: " + userId));

    }
}
