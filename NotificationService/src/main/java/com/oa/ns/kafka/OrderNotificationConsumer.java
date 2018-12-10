package com.oa.ns.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.events.Event;
import com.events.parser.JSONParser;

public class OrderNotificationConsumer {

	private final Logger logger = LoggerFactory.getLogger(OrderNotificationConsumer.class);

	@Autowired
	JSONParser parser;

	@KafkaListener(id = "OrderNotificationConsumer", topics = { "${kafka.g-order-topic}" })
	public void onMessage(ConsumerRecord<?, ?> record) {

		Event event = (Event) parser.fromJson(record.value().toString(), Event.class);
		logger.info(event.getMessage());

		// TODO Develop capability to notify the customer thru various means. Need to
		// get the communication info like email id, which is not part of the event body
		// from the customer DB.
	}
}