package com.oa.is.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.is.processor.InventoryProcessor;

public class FulfillmentErrEventConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(FulfillmentErrEventConsumer.class);

	@Autowired
	JSONParser parser;
	
	@Autowired
	InventoryProcessor inventoryProcessor;

	@KafkaListener(id = "fulfillmentErrEventConsumer", topics = { "${kafka.g-fulfillment-err-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info("Processing the rolling back of the inventory for the Order - " + event.getEventObjectId());

		// TODO Develop capability to interact with the payment gateway for making payment.
		processInventoryRollback(event);
	}
	
	private void processInventoryRollback(Event event) {
		
		inventoryProcessor.processInventoryRollback(event);		
	}

}
