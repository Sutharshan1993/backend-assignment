package com.bayzdelivery.controller;

import java.util.List;

import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.PersonService;

@RestController
@RequestMapping("/regapi")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/person")
  public ResponseEntity<Person> register(@RequestBody Person p) {
    return ResponseEntity.ok(personService.save(p));
  }

  @GetMapping("/person")
  public ResponseEntity<List<Person>> getAllPersons() {
    return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping("/person/{pers-id}")
  public ResponseEntity<Person> getPersonById(@PathVariable(name="person-id", required=true)Long personId) {
    Person person = personService.findById(personId);
    if (person != null) {
      return ResponseEntity.ok(person);
    }
    return ResponseEntity.notFound().build();
  }

}
