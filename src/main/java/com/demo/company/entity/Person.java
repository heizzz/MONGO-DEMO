package com.demo.company.entity;

import com.demo.base.MongoBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = Person.COLLECTION_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Person extends MongoBaseEntity {

    public static final String COLLECTION_NAME = "person";

    public static final String FIELD_PRS_CODE = "personCode";
    public static final String FIELD_PRS_NAME = "personName";
    public static final String FIELD_ADDRESSES = "addresses";

    @Field(value = Person.FIELD_PRS_CODE)
    private String prsCode;

    @Field(value = Person.FIELD_PRS_NAME)
    private String prsName;

    @Field(value = Person.FIELD_ADDRESSES)
    @Builder.Default
    private List<Address> addressList = new ArrayList<>();

//    public void addAddress(Address address){
////        this.addressList.add(address);
//    }
}
