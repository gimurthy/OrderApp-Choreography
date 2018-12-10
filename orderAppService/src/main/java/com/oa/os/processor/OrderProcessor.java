package com.oa.os.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.os.kafka.OrderEventProducer;

public class OrderProcessor {

	private final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

	@Autowired
	OrderEventProducer producer;

	@Autowired
	JSONParser parser;

	public void cancelOrder(Event event) {

		// TODO - Implement the logic to process the payment

		logger.info("Order cancellation processing for Order Id - " + event.getEventObjectId());
		event.setEventType("Cancelled");
		event.setMessage("Order - " + event.getEventObjectId() + " is cancelled.");
		pushEvent(false, event.getEventObjectId(), parser.toJson(event));
	}

	private void pushEvent(boolean isSuccess, String key, String event) {
		producer.sendMessage(isSuccess, key, event);
	}

}
