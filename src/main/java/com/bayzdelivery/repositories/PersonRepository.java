package com.bayzdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import com.bayzdelivery.model.Person;

@RepositoryRestResource(exported=false)
public interface PersonRepository extends JpaRepository<Person, Long>{

}
