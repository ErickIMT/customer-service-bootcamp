package com.nttdata.customerservice.application.api;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import com.nttdata.customerservice.infrastructure.data.document.CustomerType;
import com.nttdata.customerservice.infrastructure.data.service.CustomerService;
import com.nttdata.customerservice.infrastructure.data.service.CustomerTypeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @CircuitBreaker(name = "customerCB", fallbackMethod = "fallBackFindAll")
    @GetMapping
    private Flux<Customer> findAll(){
        return customerService.getAll();
    }

    @CircuitBreaker(name = "customerCB", fallbackMethod = "fallBackGetCustomerById")
    @GetMapping("/{id}")
    private Mono<Customer> getCustomerById(@PathVariable("id") String id){
        return customerService.getCustomer(id);
    }

    @CircuitBreaker(name = "customerCB", fallbackMethod = "fallBackCreate")
    @PostMapping
    private Mono<Customer> create(@RequestBody Customer customer){
        return customerService.create(customer);
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

    private Flux<Customer> fallBackFindAll(RuntimeException e){
        return Flux.error(new Throwable("El servicio tiene muchas peticiones"));
    }

    private Mono<Customer> fallBackGetCustomerById(@PathVariable("id") String id, RuntimeException e){
        return Mono.error(new Exception("No se pudo consultar el cliente id: "+ id + "\n Intente mas tarde"));
    }

    private Mono<Customer> fallBackCreate(@RequestBody Customer customer){
        return Mono.error(new Exception("No se pudo crear el cliente "+customer.getName() + ", intente mas tarde"));
    }
}
