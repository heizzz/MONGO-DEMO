package com.demo.company.controller;

import com.demo.base.BaseResponse;
import com.demo.base.ListBaseResponse;
import com.demo.base.Metadata;
import com.demo.base.SingleBaseResponse;
import com.demo.company.entity.Address;
import com.demo.company.entity.Person;
import com.demo.company.service.PersonService;
import com.demo.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/person", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createPerson(@RequestParam String storeId, @RequestParam String channelId,
                               @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
                               @RequestBody PersonCreateRequest request) throws Exception {
        this.personService.create(toPerson(request));
        return new BaseResponse(null, null, true, requestId);
    }

    @RequestMapping(value = "/person/{code}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateByPrsCode(@RequestParam String storeId, @RequestParam String channelId,
                                      @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username, @PathVariable String code,
                                      @RequestBody PersonUpdateRequest request) throws Exception {
        this.personService.update(code, toPerson(request));
        return new BaseResponse(null, null, true, requestId);
    }

    @RequestMapping(value = "/person/{code}/name", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateNameByPrsCode(@RequestParam String storeId, @RequestParam String channelId,
                                          @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username, @PathVariable String code,
                                          @RequestBody PersonUpdateNameRequest request) throws Exception {
        Person person = Person.builder().build();
        BeanUtils.copyProperties(request, person);
        this.personService.updateName(code, person);
        return new BaseResponse(null, null, true, requestId);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SingleBaseResponse<PersonResponse> findByPrsCode(@RequestParam String storeId,
                                                            @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
                                                            @RequestParam String username, @PathVariable String code) throws Exception {
        Person person = this.personService.findByCode(code);
        PersonResponse personResponse = Optional.ofNullable(person).map(this::toPersonResponse).orElse(null);
        return new SingleBaseResponse<>(null, null, true, requestId, personResponse);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deleteByPrsCode(@RequestParam String storeId, @RequestParam String channelId,
                                      @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
                                      @RequestParam String code) throws Exception {
        this.personService.deleteByEmpNo(code);
        return new BaseResponse(null, null, true, requestId);
    }

    @RequestMapping(value = {"/person"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ListBaseResponse<PersonResponse> findPersons(@RequestParam String storeId, @RequestParam String channelId,
                                                            @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
                                                            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> persons = this.personService.findAll(pageable);
        List<PersonResponse> personResponses = persons.getContent().stream().map(this::toPersonResponse).collect(Collectors.toList());
        return new ListBaseResponse<>(null, null, true, requestId, personResponses,
                new Metadata(page, size, persons.getTotalElements()));
    }

    private PersonResponse toPersonResponse(Person person) {
        return Optional.ofNullable(person).map(e -> {
            PersonResponse personResponse = PersonResponse.builder().build();
            BeanUtils.copyProperties(e, personResponse);
//            personResponse.setAddressList(toAddressResponse(person.getAddressList()));
            List<AddressResponse> temp= new ArrayList<>();
            for (int i=0; i<person.getAddressList().toArray().length; i++){
                temp.add((toAddressResponse(person.getAddressList().get(i))));
            }
            return personResponse;
        }).orElse(null);
    }

    private AddressResponse toAddressResponse(Address address) {
        return Optional.ofNullable(address).map(d -> {
            AddressResponse addressResponse = AddressResponse.builder().build();
            BeanUtils.copyProperties(d, addressResponse);
            return addressResponse;
        }).orElse(null);
    }

    private Person toPerson(PersonUpdateRequest request) {
        return Optional.ofNullable(request).map(e -> {
            Person person = Person.builder().build();
            BeanUtils.copyProperties(e, person);
            List<Address> temp= new ArrayList<>();
            for (int i=0; i<request.getAddressList().toArray().length; i++){
                temp.add((toAddress(request.getAddressList().get(i))));
            }
            return person;
        }).orElse(null);
    }

    private Person toPerson(PersonCreateRequest request) {
        return Optional.ofNullable(request).map(e -> {
            Person person = Person.builder().build();
            BeanUtils.copyProperties(e, person);
//            person.setAddressList(toAddress(request.getAddressList()));
            List<Address> temp= new ArrayList<>();
            for (int i=0; i<request.getAddressList().toArray().length; i++){
                temp.add((toAddress(request.getAddressList().get(i))));
            }
            person.setAddressList(temp);
            return person;
        }).orElse(null);
    }

    private Address toAddress(AddressRequest request) {
        return Optional.ofNullable(request).map(d -> {
            Address address = Address.builder().build();
            BeanUtils.copyProperties(d, address);
            return address;
        }).orElse(null);
    }

}
