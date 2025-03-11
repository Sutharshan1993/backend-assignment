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

@Slf4j
@RestController
@RequestMapping("/regapi")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/newRegister")
    public ResponseEntity<PersonRegisterResponse> registerNewUser(@RequestBody Person user) {
        log.info("Entered into registerNewUser : {}", user);
        PersonRegisterResponse userRegisterResponse = personService.save(user);
        log.info("Exiting from registerNewUser : {}", userRegisterResponse);
        return ResponseEntity.ok(userRegisterResponse);

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<PersonRegisterResponse>> getAllUsers() {
        log.info("Entered into getAllUsers");
        List<PersonRegisterResponse> userList = personService.getAll();
        log.info("Exiting from getAllUsers UserList : {}", userList);
        return ResponseEntity.ok(userList);
    }

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
