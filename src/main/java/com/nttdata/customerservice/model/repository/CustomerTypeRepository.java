package com.nttdata.customerservice.model.repository;

import com.nttdata.customerservice.model.document.CustomerType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends ReactiveMongoRepository<CustomerType, String> {
}
