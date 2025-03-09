package com.bayzdelivery.service;

import java.util.List;
import com.bayzdelivery.model.Person;

public interface PersonService {
  List<Person> getAll();

  Person save(Person p);

  Person findById(Long personId);

}
