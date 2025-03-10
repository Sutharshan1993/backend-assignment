package com.bayzdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bayzdelivery.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
