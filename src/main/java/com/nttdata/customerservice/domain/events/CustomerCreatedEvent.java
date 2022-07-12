package com.nttdata.customerservice.domain.events;

import com.nttdata.customerservice.infrastructure.data.document.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCreatedEvent extends Event<Customer> {

}
