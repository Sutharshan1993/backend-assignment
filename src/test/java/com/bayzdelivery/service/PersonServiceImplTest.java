package com.bayzdelivery.service;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.exceptions.PersonNotFoundException;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.PersonRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {


    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setRegistrationNumber("12345");
        person.setRole(PersonRole.CUSTOMER);
    }

    @Test
    public void testGetAll_Success() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        List<PersonRegisterResponse> response = personService.getAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(person.getId(), response.get(0).id());
        assertEquals(person.getName(), response.get(0).name());
        assertEquals(person.getRegistrationNumber(), response.get(0).registrationNumber());
        assertEquals(person.getRole(), response.get(0).role());

        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testGetAll_NoPersons() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());
        List<PersonRegisterResponse> response = personService.getAll();
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testSave_Success() {

        when(personRepository.save(person)).thenReturn(person);
        PersonRegisterResponse response = personService.save(person);
        assertNotNull(response);
        assertEquals(person.getId(), response.id());
        assertEquals(person.getName(), response.name());
        assertEquals(person.getRegistrationNumber(), response.registrationNumber());
        assertEquals(person.getRole(), response.role());

        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testSave_InvalidRole() {
        person.setRole(PersonRole.ADMIN);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.save(person);
        });
        assertEquals("Role must be either CUSTOMER or DELIVERY_MAN", exception.getMessage());

        verify(personRepository, never()).save(any());
    }

    @Test
    public void testFindById_Success() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        PersonRegisterResponse response = personService.findById(person.getId());
        assertNotNull(response);
        assertEquals(person.getId(), response.id());
        assertEquals(person.getName(), response.name());
        assertEquals(person.getRegistrationNumber(), response.registrationNumber());
        assertEquals(person.getRole(), response.role());

        verify(personRepository, times(1)).findById(person.getId());
    }

    @Test
    public void testFindById_PersonNotFound() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.empty());
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> {
            personService.findById(person.getId());
        });
        assertEquals("Person not found with ID: " + person.getId(), exception.getMessage());

        verify(personRepository, times(1)).findById(person.getId());
    }
}
