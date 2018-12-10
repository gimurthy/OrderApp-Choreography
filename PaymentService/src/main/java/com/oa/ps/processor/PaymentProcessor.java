package com.oa.ps.processor;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.ps.kafka.PaymentEventProducer;

public class PaymentProcessor {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

	@Autowired
	PaymentEventProducer producer;
	
	@Autowired
	JSONParser parser;

	public void processPayment(Event event) {

		Random r = new Random();

		// TODO - Implement the logic to process the payment

		if (r.nextBoolean()) {
			logger.info("Payment processing successful. Order Id - " + event.getEventObjectId());
			event.setEventType("Payment_Processed");
			event.setMessage("Payment for order - " +event.getEventObjectId()+ " successfully processed.");
			pushEvent(true, event.getEventObjectId(), parser.toJson(event));
		} else {
			logger.info("Payment processing not successful. Order Id - " + event.getEventObjectId());	
			event.setEventType("Payment_Failed");
			event.setMessage("Payment for order - " +event.getEventObjectId()+ " Failed.");
			pushEvent(false, event.getEventObjectId(), parser.toJson(event));
		}
	}
	
	public void processPaymentRollback(Event event) {

		// TODO - Implement the logic to process the payment

		logger.info("Payment Rollback processing for Order Id - " + event.getEventObjectId());
		event.setEventType("Payment_Rollback");
		event.setMessage("Payment for order - " + event.getEventObjectId() + " is Rolled back.");
		pushEvent(false, event.getEventObjectId(), parser.toJson(event));
	}
	
	private void pushEvent(boolean isSuccess, String key, String event) {
		producer.sendMessage(isSuccess, key, event);
	}

}
