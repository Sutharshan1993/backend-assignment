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