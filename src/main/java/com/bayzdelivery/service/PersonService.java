package com.bayzdelivery.service;

import java.util.List;

import com.bayzdelivery.dto.PersonRegisterResponse;
import com.bayzdelivery.model.Person;

public interface PersonService {
  List<PersonRegisterResponse> getAll();

  PersonRegisterResponse save(Person p);

  PersonRegisterResponse findById(Long personId);

}
