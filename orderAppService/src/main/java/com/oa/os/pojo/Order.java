package com.oa.os.pojo;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

public class Order {
	
	@Getter @Setter @NonNull private String customerId;
	@Getter @Setter @NonNull private String productSKU;
	@Getter @Setter private int qty;
	@Getter @Setter private double cost;
	@Getter @Setter @NonNull private String deliveryAddress;	

}
