package com.oa.is.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.is.processor.InventoryProcessor;

public class PaymentEventConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

	@Autowired
	JSONParser parser;
	
	@Autowired
	InventoryProcessor inventoryProcessor;

	@KafkaListener(id = "paymentEventConsumer", topics = { "${kafka.g-payment-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info("Processing the inventory for the Order - " + event.getEventObjectId());

		// TODO Develop capability to interact with the payment gateway for making payment.
		processInventory(event);
	}
	
	private void processInventory(Event event) {
		
		inventoryProcessor.processInventory(event);		
	}

}
