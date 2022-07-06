package com.nttdata.customerservice.infrastructure.data.service;

import com.nttdata.customerservice.infrastructure.data.document.CustomerType;
import com.nttdata.customerservice.infrastructure.data.repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService{

    @Autowired
    CustomerTypeRepository customerTypeRepository;

    @Override
    public Mono<CustomerType> create(CustomerType customerType) {
        return customerTypeRepository.save(customerType);
    }

    @Override
    public Flux<CustomerType> getAll() {
        return customerTypeRepository.findAll();
    }

    @Override
    public Mono<CustomerType> update(CustomerType customerType) {
        return getCustomerType(customerType.getId())
                .switchIfEmpty(Mono.error(new Exception("CUSTOMER_TYPE_NOT_FOUND")))
                .map(c -> {
                    c.setName(customerType.getName());
                    return c;
                }).flatMap(c1 -> customerTypeRepository.save(c1));
    }

    @Override
    public void delete(String id) {

        customerTypeRepository.deleteById(id);
    }

    @Override
    public Mono<CustomerType> getCustomerType(String id) {
        return customerTypeRepository.findById(id);
    }


}
