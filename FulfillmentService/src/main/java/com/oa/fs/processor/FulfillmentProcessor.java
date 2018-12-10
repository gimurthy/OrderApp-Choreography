package com.oa.fs.processor;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.fs.kafka.FulfillmentEventProducer;

public class FulfillmentProcessor {

	private final Logger logger = LoggerFactory.getLogger(FulfillmentProcessor.class);

	@Autowired
	FulfillmentEventProducer producer;

	@Autowired
	JSONParser parser;

	public void processFulfillment(Event event) {

		Random r = new Random();

		// TODO - Implement the logic to process the payment

		if (r.nextBoolean()) {
			logger.info("Fulfillment processing successful. Order Id - " + event.getEventObjectId());
			event.setEventType("Fulfillment_Completed");
			event.setMessage("Fulfillment for order - " + event.getEventObjectId() + " is completed.");
			pushEvent(true, event.getEventObjectId(), parser.toJson(event));
		} else {
			logger.info("Inventory processing not successful. Order Id - " + event.getEventObjectId());
			event.setEventType("Fulfillment_Failed");
			event.setMessage("Fulfillment for order - " + event.getEventObjectId() + " has Failed.");
			pushEvent(false, event.getEventObjectId(), parser.toJson(event));
		}
	}

	private void pushEvent(boolean isSuccess, String key, String event) {
		producer.sendMessage(isSuccess, key, event);
	}
}
