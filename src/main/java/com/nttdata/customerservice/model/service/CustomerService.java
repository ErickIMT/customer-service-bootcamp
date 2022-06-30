package com.nttdata.customerservice.model.service;

import com.nttdata.customerservice.model.document.Customer;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> create(Customer customer);
    Flux<Customer>getAll();
    Mono<Customer> update(Customer customer);
    Mono<Boolean> delete(String id);
    Mono<Customer> getCustomer(String id);
}
