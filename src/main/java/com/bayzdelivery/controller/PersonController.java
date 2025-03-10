package com.bayzdelivery.controller;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regapi")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<PersonRegisterResponse> register(@RequestBody Person p) {
        PersonRegisterResponse personRegisterResponse = personService.save(p);
        return ResponseEntity.ok(personRegisterResponse);

    }

    @GetMapping("/getAllpersons")
    public ResponseEntity<List<PersonRegisterResponse>> getAllPersons() {
        List<PersonRegisterResponse> personRegisterResponse = personService.getAll();
        return ResponseEntity.ok(personRegisterResponse);
    }

    @GetMapping("/person/{person-id}")
    public ResponseEntity<PersonRegisterResponse> getPersonById(@PathVariable(name = "person-id", required = true) Long personId) {
        PersonRegisterResponse person = personService.findById(personId);
        if ( person != null ) {
            return ResponseEntity.ok(person);
        }
        return ResponseEntity.notFound().build();
    }

}
