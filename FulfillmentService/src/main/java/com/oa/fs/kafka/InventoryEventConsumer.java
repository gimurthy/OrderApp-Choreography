package com.oa.fs.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.fs.processor.FulfillmentProcessor;

public class InventoryEventConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);

	@Autowired
	JSONParser parser;
	
	@Autowired
	FulfillmentProcessor fulfillmentProcessor;

	@KafkaListener(id = "inventoryEventConsumer", topics = { "${kafka.g-inventory-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info("Processing the fulfillment for the Order - " + event.getEventObjectId());

		// TODO Develop capability to interact with the payment gateway for making payment.
		processFulfillment(event);
	}
	
	private void processFulfillment(Event event) {
		
		fulfillmentProcessor.processFulfillment(event);		
	}

}
