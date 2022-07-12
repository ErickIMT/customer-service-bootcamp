package com.nttdata.customerservice.infrastructure.data.service;

import com.nttdata.customerservice.domain.events.CustomerCreatedEvent;
import com.nttdata.customerservice.domain.events.Event;
import com.nttdata.customerservice.domain.events.EventType;
import com.nttdata.customerservice.infrastructure.data.document.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Servicio para enviar informacion por KAFKA.
 */
@Component
public class CustomerEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.customer.name:customers}")
	private String topicCustomer;
	
	public void publish(Customer customer) {

		CustomerCreatedEvent created = new CustomerCreatedEvent();
		created.setData(customer);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());

		this.producer.send(topicCustomer, created);
	}
}
