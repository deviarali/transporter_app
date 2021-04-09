package com.transporter.enums;

public enum DeliveryStatusEnum {

	PENDING(1), TRIPACCEPTED(2), DRIVERREACHEDPICKUPLOCATION(3), ONGOING(4), DRIVERREACHEDDESTINATIONLOCATION(5), 
	TRIPENDED(6), TRIPRATINGS(7), TRIPCOMPLETED(8), TRIPREJECTED(9), CANCELLEDBYCUSTOMER(10), CANCELLEDBYDRIVER(11), CANCELLED(12);
	
	private int deliveryStatusId;

	private DeliveryStatusEnum(int deliveryStatusId) {

		this.deliveryStatusId = deliveryStatusId;
	}

	public int getId() {
		return deliveryStatusId;
	}
}
