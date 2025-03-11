package com.bayzdelivery.controller;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PersonController handles requests related to the Person entity.
 * It provides endpoints to create, retrieve, and manage registered users.
 * <p>
 * The controller maps its endpoints under the "/regapi" base path.
 * Each method includes logging for entry, exit, and key actions to ensure traceability.
 * <p>
 * Dependencies:
 * - PersonService is used to handle business logic and interaction with the persistence layer.
 * <p>
 * Endpoints:
 * 1. registerNewUser: Handles POST requests to register a new user.
 * 2. getAllUsers: Handles GET requests to fetch a list of all registered users.
 * 3. getUserById: Handles GET requests to fetch the details of a specific user based on the user ID.
 */
@Slf4j
@RestController
@RequestMapping("/regapi")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    /**
     * Registers a new user by saving their details and returns the response.
     *
     * @param user the details of the person to be registered
     * @return a ResponseEntity containing the registration details of the person
     */
    @PostMapping("/newRegister")
    public ResponseEntity<PersonRegisterResponse> registerNewUser(@RequestBody Person user) {
        log.info("Entered into registerNewUser : {}", user);
        PersonRegisterResponse userRegisterResponse = personService.save(user);
        log.info("Exiting from registerNewUser : {}", userRegisterResponse);
        return ResponseEntity.ok(userRegisterResponse);

    }

    /**
     * Retrieves all registered users.
     * <p>
     * This method fetches a list of all registered users and returns it as a response.
     * Logs are generated at both entry and exit points for traceability.
     *
     * @return a ResponseEntity containing a list of PersonRegisterResponse objects,
     * each representing a registered user's details
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<PersonRegisterResponse>> getAllUsers() {
        log.info("Entered into getAllUsers");
        List<PersonRegisterResponse> userList = personService.getAll();
        log.info("Exiting from getAllUsers UserList : {}", userList);
        return ResponseEntity.ok(userList);
    }

    /**
     * Retrieves the user details for the specified user ID.
     *
     * @param userId the unique identifier of the user to retrieve
     * @return a ResponseEntity containing the user details if found,
     * or a 404 Not Found response if no user exists with the provided ID
     */
    @GetMapping("/user/{user-id}")
    public ResponseEntity<PersonRegisterResponse> getUserById(@PathVariable(name = "user-id") @NotNull Long userId) {
        log.info("Entered into getUserById for UserId :{}", userId);
        PersonRegisterResponse userDetail = personService.findById(userId);
        if ( userDetail != null ) {
            log.info("Exiting from getUserById for UserId :{}, userDetail:{}", userId, userDetail);
            return ResponseEntity.ok(userDetail);
        }
        return ResponseEntity.notFound().build();
    }

}
