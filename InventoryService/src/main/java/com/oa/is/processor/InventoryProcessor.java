package com.oa.is.processor;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.is.kafka.InventoryEventProducer;

public class InventoryProcessor {

	private final Logger logger = LoggerFactory.getLogger(InventoryProcessor.class);

	@Autowired
	InventoryEventProducer producer;

	@Autowired
	JSONParser parser;

	public void processInventory(Event event) {

		Random r = new Random();

		// TODO - Implement the logic to process the payment

		if (r.nextBoolean()) {
			logger.info("Inventory processing successful. Order Id - " + event.getEventObjectId());
			event.setEventType("Inventory_Available");
			event.setMessage("Inventory for order - " + event.getEventObjectId() + " available & blocked.");
			pushEvent(true, event.getEventObjectId(), parser.toJson(event));
		} else {
			logger.info("Inventory processing not successful. Order Id - " + event.getEventObjectId());
			event.setEventType("Inventory_OOS");
			event.setMessage("Inventory for order - " + event.getEventObjectId() + " is Out of Stock.");
			pushEvent(false, event.getEventObjectId(), parser.toJson(event));
		}
	}

	public void processInventoryRollback(Event event) {

		// TODO - Implement the logic to process the payment

		logger.info("Inventory Rollback processing for Order Id - " + event.getEventObjectId());
		event.setEventType("Inventory_Rollback");
		event.setMessage("Inventory for order - " + event.getEventObjectId() + " is Rolled back.");
		pushEvent(false, event.getEventObjectId(), parser.toJson(event));
	}

	private void pushEvent(boolean isSuccess, String key, String event) {
		producer.sendMessage(isSuccess, key, event);
	}
}
