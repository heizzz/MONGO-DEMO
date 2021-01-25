package com.demo.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Address implements Serializable {
    public static final String FIELD_ADR_NAME = "addressName";
    public static final String FIELD_ADR = "address";
    public static final String FIELD_CITY = "city";

    @Field(value = Address.FIELD_ADR_NAME)
    private String adrName;

    @Field(value = Address.FIELD_ADR)
    private String adr;

    @Field(value = Address.FIELD_CITY)
    private String city;

}
