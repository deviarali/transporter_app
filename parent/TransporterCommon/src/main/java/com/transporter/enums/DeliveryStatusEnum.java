package com.transporter.enums;

public enum DeliveryStatusEnum {

	PENDING(1), ONGOING(2), CANCELEDBYDRIVER(3), CANCELEDBYCUSTOMER(4), COMPLETED(5);
	
	private int deliveryStatusId;

	private DeliveryStatusEnum(int deliveryStatusId) {

		this.deliveryStatusId = deliveryStatusId;
	}

	public int getId() {
		return deliveryStatusId;
	}
}
