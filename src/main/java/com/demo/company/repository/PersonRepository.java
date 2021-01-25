package com.demo.company.repository;

import com.demo.company.entity.Employee;
import com.demo.company.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String>, PersonCustomRepository {
    Person findFirstByprsCode(String prsCode);

    Page<Person>findAll(Pageable pageable);

    void deleteByprsCode(String prsCode);
}
