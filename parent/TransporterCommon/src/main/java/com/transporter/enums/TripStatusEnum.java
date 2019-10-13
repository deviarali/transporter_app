package com.transporter.enums;

public enum TripStatusEnum {

	PENDING(1), ONGOING(2), CANCELEDBYDRIVER(3), CANCELEDBYCUSTOMER(4), COMPLETED(5);
	
	int tripStatusId;
	private TripStatusEnum(int tripStatus) {
		
		tripStatusId = tripStatus;

	}
	public int getTripStatusId() {
		return tripStatusId;
	}
	
}
