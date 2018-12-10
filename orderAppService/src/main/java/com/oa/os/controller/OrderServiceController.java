package com.oa.os.controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.events.Event;
import com.events.IEvent;
import com.events.order.OrderEvent;
import com.events.parser.JSONParser;
import com.oa.os.kafka.OrderEventProducer;
import com.oa.os.pojo.Order;

@RestController
@RequestMapping("/order")
public class OrderServiceController {

	private final Logger logger = LoggerFactory.getLogger(OrderServiceController.class);

	private String orderId;

	@Autowired
	OrderEventProducer producer;

	@Autowired
	JSONParser parser;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody Order order) {

		logger.info("Started Order processing.");

		ResponseEntity<String> re = processOrder(order);

		return re;
	}

	private ResponseEntity<String> processOrder(Order order) {

		ResponseEntity<String> re = null;

		Random r = new Random();
		int min = 1000000;
		orderId = "o" + r.nextInt(min);

		// TODO - Implement the logic to process and persist the order

		if (r.nextBoolean()) {

			logger.info("Order creation successful. Order Id - " + orderId);

			String event = createSuccessEvent(order, orderId);
			logger.info(event);
			pushEvent(true, orderId, event);

			re = new ResponseEntity<>("{'order':'" + orderId + "'}", HttpStatus.OK);
		} else {

			logger.info("Order creation not successful.");
			re = new ResponseEntity<>("Order creation not successful.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return re;

	}

	private String createSuccessEvent(Order order, String orderId) {

		// Create Payload
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setCustomerId(order.getCustomerId());
		orderEvent.setProductSKU(order.getProductSKU());
		orderEvent.setCost(order.getCost());
		orderEvent.setQty(order.getQty());
		orderEvent.setDeliveryAddress(order.getDeliveryAddress());

		// Create Event
		Event event = new Event();
		event.setEventObject("Order");
		event.setEventObjectId(orderId);
		event.setEventType("Created");
		event.setPayload(parser.toJson(orderEvent));
		event.setMessage("The Order - " + orderId + " Successfully Placed");

		return parser.toJson(event);

	}

	private String createFailureEvent(Order order, String orderId) {

		// Create Payload
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setCustomerId(order.getCustomerId());
		orderEvent.setProductSKU(order.getProductSKU());
		orderEvent.setCost(order.getCost());
		orderEvent.setQty(order.getQty());
		orderEvent.setDeliveryAddress(order.getDeliveryAddress());

		// Create Event
		Event event = new Event();
		event.setEventObject("Order");
		event.setEventType("Cancelled");
		event.setPayload(parser.toJson(orderEvent));
		if (orderId == null || orderId.equals(""))
			event.setMessage("The Order - " + orderId + " Successfully Placed");
		else {
			event.setMessage("The Order creation did not happen. Try again!!");
			event.setEventObjectId(orderId);
		}

		return parser.toJson(event);

	}

	private void pushEvent(boolean isSuccess, String key, String event) {
		producer.sendMessage(isSuccess, key, event);
	}
}