package com.mybank.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private Integer id;
    private String name;
    private String address;
    private Integer age;
    private Integer familyMembersCount;
}
