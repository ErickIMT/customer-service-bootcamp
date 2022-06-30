package com.nttdata.customerservice.model.service;

import com.nttdata.customerservice.model.document.Customer;
import com.nttdata.customerservice.model.document.CustomerType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerTypeService {

    Mono<CustomerType> create(CustomerType customerType);
    Flux<CustomerType> getAll();
    Mono<CustomerType> update(CustomerType customerType);
    void delete(String id);
    Mono<CustomerType> getCustomerType(String id);
}
