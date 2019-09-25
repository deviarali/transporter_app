package com.transporter.fcm;

public enum NotificationType {
	COUPON_CODE_ADDED("coupon_code_added"),
	NEW_TRIP_AVAILABLE("new_trip_availabed"),
	BOOKING_CONFIRMED("booking_confirmed"),
	BOOKING_CANCELLED("booking_cancelled"),
	PROMOTION("promotion"),
	DRIVER_REACHED_PICK_UP_POIN("driver_reached_pick_up_point"),
	TRIP_STARTED("trip_started"),
	DRIVER_REACHED_DESTINATION("driver_reached_destination"),
	TRIP_END("trip_end"),
	GOODS_DELIVERED("goods_delivered");

	private String value;

	public String getValue() {
		return value;
	}

	private NotificationType(String value) {
		this.value = value;
	}
}
