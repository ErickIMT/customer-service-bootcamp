package com.nttdata.customerservice.infrastructure.data.repository;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
