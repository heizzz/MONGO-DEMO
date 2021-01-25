package com.demo.company.service;

import com.demo.company.entity.Employee;
import com.demo.company.entity.Person;
import com.demo.company.repository.PersonRepository;
import com.demo.config.data.Credential;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceBean implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void create(Person person) throws Exception {
        person.setStoreId(MDC.get(Credential.CREDENTIAL_STORE_ID));
        personRepository.save(person);
    }

    @Override
    public void update(String prsCode, Person person) throws Exception {
        Person oldPrs = personRepository.findFirstByprsCode(prsCode);
        oldPrs.setPrsName(person.getPrsName());
        oldPrs.setAddressList(person.getAddressList());
        personRepository.save(oldPrs);
    }

    @Override
    public void updateName(String code, Person person) throws Exception {
        Person oldPrs = personRepository.findFirstByprsCode(code);
        oldPrs.setPrsName(person.getPrsName());
        personRepository.save(oldPrs);
    }

    @Override
    public Page<Person> findAll(Pageable pageable) throws Exception {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person findByCode(String code) throws Exception {
        return personRepository.findFirstByprsCode(code);
    }

    @Override
    public void deleteByEmpNo(String code) throws Exception {
        this.personRepository.deleteByprsCode(code);
    }
}
