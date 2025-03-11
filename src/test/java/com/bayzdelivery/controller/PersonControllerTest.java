package com.bayzdelivery.controller;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import com.bayzdelivery.utils.PersonRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonController.
 * <p>
 * This class contains unit test cases for testing the functionality of
 * the PersonController using the Spring MockMvc framework and Mockito for mocking dependencies.
 * <p>
 * Key Features:
 * - Verifies that the controller interacts correctly with the PersonService.
 * - Ensures correct HTTP response codes and payload structures for each endpoint.
 * - Uses the ObjectMapper for JSON serialization/deserialization.
 * <p>
 * Dependencies:
 * - Mocks the PersonService to isolate and test controller behavior independently.
 * - Uses MockMvc to simulate HTTP requests and validate responses.
 * <p>
 * Test Methods:
 * 1. registerNewUser_shouldReturnOkAndPersonRegisterResponse:
 * Tests that registering a new user returns an HTTP 200 status and the expected PersonRegisterResponse.
 * 2. getAllUsers_shouldReturnOkAndListOfPersonRegisterResponses:
 * Verifies that fetching all users returns an HTTP 200 status and a list of user registration responses.
 * 3. getPersonById_shouldReturnOkAndPersonRegisterResponse_whenPersonExists:
 * Tests that fetching a user by ID returns an HTTP 200 status and the corresponding user details when the user exists.
 * 4. getPersonById_shouldReturnNotFound_whenPersonDoesNotExist:
 * Validates that fetching a user by a non-existent ID returns an HTTP 404 status.
 */
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerNewUser_shouldReturnOkAndPersonRegisterResponse() throws Exception {
        Person person = new Person();
        person.setId(1L);
        PersonRegisterResponse response = new PersonRegisterResponse(1L, "TestMe", "REG12547", PersonRole.CUSTOMER);

        when(personService.save(any(Person.class))).thenReturn(response);

        mockMvc.perform(post("/regapi/newRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(personService, times(1)).save(any(Person.class));
    }

    @Test
    void getAllUsers_shouldReturnOkAndListOfPersonRegisterResponses() throws Exception {
        PersonRegisterResponse response1 = new PersonRegisterResponse(1L, "TestMe", "REG12547", PersonRole.CUSTOMER);

        PersonRegisterResponse response2 = new PersonRegisterResponse(2L, "TestMe1", "REG12558", PersonRole.DELIVERY_MAN);

        List<PersonRegisterResponse> responses = Arrays.asList(response1, response2);

        when(personService.getAll()).thenReturn(responses);

        mockMvc.perform(get("/regapi/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(personService, times(1)).getAll();
    }

    @Test
    void getPersonById_shouldReturnOkAndPersonRegisterResponse_whenPersonExists() throws Exception {
        PersonRegisterResponse response = new PersonRegisterResponse(1L, "TestMe", "REG12547", PersonRole.CUSTOMER);


        when(personService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/regapi/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(personService, times(1)).findById(1L);
    }

    @Test
    void getPersonById_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        when(personService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/regapi/user/1"))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).findById(1L);
    }
}