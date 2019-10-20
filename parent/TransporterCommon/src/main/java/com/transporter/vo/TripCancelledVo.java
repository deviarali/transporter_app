package com.transporter.vo;

public class TripCancelledVo {

	private int tripId;
	
	private String cancelledReason;
	
	private int deliveryStatusId;

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	public int getDeliveryStatusId() {
		return deliveryStatusId;
	}

	public void setDeliveryStatusId(int deliveryStatusId) {
		this.deliveryStatusId = deliveryStatusId;
	}
	
	
	
	
	
	
}
