package com.events.order;

import com.events.IEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent implements IEvent {
	
	@Getter @Setter @NonNull private String customerId;
	@Getter @Setter @NonNull private String productSKU;
	@Getter @Setter private int qty;
	@Getter @Setter private double cost;
	@Getter @Setter @NonNull private String deliveryAddress;	
}
