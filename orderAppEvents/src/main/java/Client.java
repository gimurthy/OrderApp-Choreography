import java.util.Random;

import com.events.Event;
import com.events.order.OrderEvent;
import com.events.parser.JSONParser;

public class Client {

	public static void main(String[] args) {

		Random randomno = new Random();
		boolean value = randomno.nextBoolean();
		System.out.println("Value is: " + value);

	}

	public static void getEvent() {
		JSONParser parser = new JSONParser();

		// String json = "{\"eventObject\":\"OrderEvent
		// \",\"eventObjectId\":123,\"eventType\":\"\",\"timestamp\":\"12/05/2018\",\"payload\":\"Some
		// Text\",\"Message\":\"\"}";

		Event event = new Event();
		event.setEventObject("Order");
		event.setEventObjectId("12345");
		event.setEventType("Created");
		event.setMessage("Done");
		event.setPayload(getPayload());

		String json = parser.toJson(event);
		Event e = (Event) parser.fromJson(json, Event.class);

		String payload = (String) e.getPayload();
		OrderEvent oe1 = (OrderEvent) parser.fromJson(payload, OrderEvent.class);

		System.out.println("Result - " + oe1.getCustomerId());
	}

	public static String getPayload() {

		JSONParser parser = new JSONParser();

		// String json =
		// "{\"customerId\":\"gimu\",\"productSKU\":\"SKU0001\",\"qty\":2,\"cost\":100,\"deliveryAddress\":\"Blore\"}";

		OrderEvent oe = new OrderEvent();
		oe.setCost(100.00);
		oe.setCustomerId("gimu");
		oe.setDeliveryAddress("Blore");
		oe.setProductSKU("SKU001");
		oe.setQty(2);

		return parser.toJson(oe);

	}

}
