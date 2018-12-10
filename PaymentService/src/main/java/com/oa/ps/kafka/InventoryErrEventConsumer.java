package com.oa.ps.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.ps.processor.PaymentProcessor;

public class InventoryErrEventConsumer {

	private final Logger logger = LoggerFactory.getLogger(InventoryErrEventConsumer.class);

	@Autowired
	JSONParser parser;
	
	@Autowired
	PaymentProcessor paymentProcessor;

	@KafkaListener(id = "inventoryErrEventConsumer", topics = { "${kafka.g-inventory-err-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info("Processing the rolling back of the payment for the Order - " + event.getEventObjectId());

		// TODO Develop capability to interact with the payment gateway for making payment.
		processPaymentRollback(event);
	}
	
	private void processPaymentRollback(Event event) {
		
		paymentProcessor.processPaymentRollback(event);		
	}
}