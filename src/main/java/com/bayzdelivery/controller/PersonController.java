package com.bayzdelivery.controller;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regapi")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/newRegister")
    public ResponseEntity<PersonRegisterResponse> registerNewUser(@RequestBody Person p) {
        PersonRegisterResponse personRegisterResponse = personService.save(p);
        return ResponseEntity.ok(personRegisterResponse);

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<PersonRegisterResponse>> getAllUsers() {
        List<PersonRegisterResponse> personRegisterResponse = personService.getAll();
        return ResponseEntity.ok(personRegisterResponse);
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<PersonRegisterResponse> getPersonById(@PathVariable(name = "user-id") @NotNull Long personId) {
        PersonRegisterResponse person = personService.findById(personId);
        if ( person != null ) {
            return ResponseEntity.ok(person);
        }
        return ResponseEntity.notFound().build();
    }

}
