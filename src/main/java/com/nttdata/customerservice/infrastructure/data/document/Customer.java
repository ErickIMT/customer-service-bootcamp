package com.nttdata.customerservice.infrastructure.data.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String document;
    private String address;
    private String email;
    private String phone;
    private CustomerType customerType;

}
