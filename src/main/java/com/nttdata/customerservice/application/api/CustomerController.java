package com.nttdata.customerservice.application.api;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import com.nttdata.customerservice.infrastructure.data.document.CustomerType;
import com.nttdata.customerservice.infrastructure.data.service.CustomerService;
import com.nttdata.customerservice.infrastructure.data.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @PostMapping
    private Mono<Customer> create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping
    private Flux<Customer> findAll(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    private Mono<Customer> getCustomerById(@PathVariable("id") String id){
        return customerService.getCustomer(id);
    }

    @PutMapping
    private Mono<Customer> update(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") String id){
        customerService.delete(id);
        return "Borrado";
    }

    @GetMapping("/type")
    private Flux<CustomerType> getAllTypeCustomers(){
        return customerTypeService.getAll();
    }

    @GetMapping("/type/{id}")
    private Mono<CustomerType> getcustomerType(@PathVariable("id") String id){
        return customerTypeService.getCustomerType(id);
    }

    @PostMapping("/type")
    private Mono<CustomerType> saveType(@RequestBody CustomerType customerType){
        return  customerTypeService.create(customerType);
    }
}
