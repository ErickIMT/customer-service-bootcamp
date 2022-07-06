package com.nttdata.customerservice.infrastructure.data.repository;

import com.nttdata.customerservice.infrastructure.data.document.CustomerType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends ReactiveMongoRepository<CustomerType, String> {
}
