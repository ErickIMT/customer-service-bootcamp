package com.nttdata.customerservice.model.service;

import com.nttdata.customerservice.model.document.Customer;
import com.nttdata.customerservice.model.document.CustomerType;
import com.nttdata.customerservice.model.repository.CustomerRepository;
import com.nttdata.customerservice.model.repository.CustomerTypeRepository;
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
        .map(c -> {
          LOGGER.info("Se encontro Lista de usuario");

          if (c.getCustomerType().getId().equals("62b22c20dc9c89516501fdbc")) {
            LOGGER.info("Seteando CustomerType Personal");
            c.getCustomerType().setName("Personal");
          } else if (c.getCustomerType().getId().equals("62b22c27dc9c89516501fdbd")) {
            LOGGER.info("Seteando CustomerType Empresarial");
            c.getCustomerType().setName("Empresarial");
          } else {
            LOGGER.warn("customerType vacio o Null");
            return c;
          }

          /* customerTypeService.getCustomerType(c.getCustomerType().getId())
               .(customerType -> {
                                        System.out.println(customerType.getName());
                                        c.setCustomerType(customerType);
                              });
          */
          return c;
        });
  }

  @Override
  public Mono<Customer> update(Customer customer) {
    LOGGER.info("Iniciando Actualizacion de Cliente");
    LOGGER.info("Buscando Id de Cliente");
    return getCustomer(customer.getId())
        .switchIfEmpty(Mono.error(new Exception("CUSTOMER_NOT_FOUND")))
        .map(customer1 -> {
          LOGGER.info("Se encontro Cliente con id" + customer1.getId());
          LOGGER.info("Modificando Datos");
          customer1.setName(customer.getName());
          customer1.setLastName(customer.getLastName());
          customer1.setAddress(customer.getAddress());
          customer1.setEmail(customer.getEmail());
          customer1.setDocument(customer.getDocument());

          return customer1;
        }).flatMap(c -> {
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
        .map(c -> {
          LOGGER.info("Se consiguio cliente con id " + c.getId());
          LOGGER.info("seteando nombre de customerType");
          if (c.getCustomerType().getId().equals("62b22c20dc9c89516501fdbc")) {
            c.getCustomerType().setName("Personal");
          } else if (c.getCustomerType().getId().equals("62b22c27dc9c89516501fdbd")) {
            c.getCustomerType().setName("Empresarial");
          } else {
            LOGGER.warn("Campo customerType vacio o Nulo");
            return c;
          }

          /*System.out.println(c.getCustomerType().getId());

                   customerTypeService.getCustomerType(c.getCustomerType().getId())
                           .doOnNext(customerType -> {

                               System.out.println(customerType.getName());

                               c.setCustomerType(customerType);
                               System.out.println("DENTRO: "+c.getCustomerType().getName());
          });*/

          return c;
        });
  }
}
