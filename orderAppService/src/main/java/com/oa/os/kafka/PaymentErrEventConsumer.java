package com.oa.os.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;
import com.oa.os.processor.OrderProcessor;

public class PaymentErrEventConsumer {

	private final Logger logger = LoggerFactory.getLogger(PaymentErrEventConsumer.class);

	@Autowired
	JSONParser parser;
	
	@Autowired
	OrderProcessor orderProcessor;

	@KafkaListener(id = "paymentErrEventConsumer", topics = { "${kafka.g-payment-err-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info("Processing the cancellation of the Order - " + event.getEventObjectId());

		// TODO Develop capability to interact with the payment gateway for making payment.
		processOrderCancellation(event);
	}
	
	private void processOrderCancellation(Event event) {
		
		orderProcessor.cancelOrder(event);		
	}
}