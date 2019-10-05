package com.transporter.enums;

public enum DeliveryStatusEnum {

	PENDING(1), ON_GOING(2), COMPLETED(3);
	
	private int deliveryStatusId;

	private DeliveryStatusEnum(int deliveryStatusId) {

		this.deliveryStatusId = deliveryStatusId;
	}

	public int getId() {
		return deliveryStatusId;
	}
}
