package com.nttdata.customerservice.infrastructure.data.service;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import com.nttdata.customerservice.infrastructure.data.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase implementadora de CustomerService.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerTypeService customerTypeService;

  @Override
  public Mono<Customer> create(Customer customer) {
    LOGGER.info("GUARDANDO CLIENTE : \n"
        + "NOMBRE: " + customer.getName());
    return customerRepository.save(customer);
  }

  @Override
  public Flux<Customer> getAll() {

    LOGGER.info("Iniciando busqueda de Lista de Clientes");
    return customerRepository.findAll()
        .switchIfEmpty(Flux.error(new Exception("CUSTOMERS_NOT_FOUND")))
        .map(this::setTypeOnCustomer);
  }

  @Override
  public Mono<Customer> update(Customer customer) {
    LOGGER.info("Iniciando Actualizacion de Cliente");
    LOGGER.info("Buscando Id de Cliente");
    return getCustomer(customer.getId())
        .switchIfEmpty(Mono.error(new Exception("CUSTOMER_NOT_FOUND")))
        .map(this::setUpdateDataCustomer)
        .flatMap(c -> {
          LOGGER.info("Guardando Cliente con datos modificados");
          return customerRepository.save(c);
        });
  }

  @Override
  public Mono<Boolean> delete(String id) {

    return getCustomer(id)
        .flatMap(c -> customerRepository.delete(c).then(Mono.just(Boolean.TRUE)))
        .defaultIfEmpty(Boolean.FALSE);

  }

  @Override
  public Mono<Customer> getCustomer(String id) {
    LOGGER.info("Buscando Cliente por Id");
    return customerRepository.findById(id)
        .map(this::setTypeOnCustomer);
  }

  private Customer setTypeOnCustomer(Customer customer){


    if (customer.getCustomerType().getId().equals("62b22c20dc9c89516501fdbc")) {
      LOGGER.info("Seteando CustomerType Personal");
      customer.getCustomerType().setName("Personal");
    } else if (customer.getCustomerType().getId().equals("62b22c27dc9c89516501fdbd")) {
      LOGGER.info("Seteando CustomerType Empresarial");
      customer.getCustomerType().setName("Empresarial");
    } else {
      LOGGER.warn("customerType vacio o Null");
      return customer;
    }
    return customer;

    /*
    String id = customer.getId();
    Mono<CustomerType> customerType = customerTypeService.getCustomerType(id);
    customerType.map(ct -> {
      customer.setCustomerType(ct);
      return customer;
    }).subscribe(customer1 -> LOGGER.info("Seteando CustomerType: "+customer.getCustomerType().getName()));

    return customer;*/
  }

  private Customer setUpdateDataCustomer(Customer customer){
    customer.setName(customer.getName());
    customer.setLastName(customer.getLastName());
    customer.setAddress(customer.getAddress());
    customer.setEmail(customer.getEmail());
    customer.setDocument(customer.getDocument());

    return customer;
  }
}
