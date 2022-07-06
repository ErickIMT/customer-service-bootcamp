package com.nttdata.customerservice.infrastructure.data.service;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> create(Customer customer);
    Flux<Customer>getAll();
    Mono<Customer> update(Customer customer);
    Mono<Boolean> delete(String id);
    Mono<Customer> getCustomer(String id);
}
