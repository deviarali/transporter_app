package com.transporter.enums;

public enum RidingStatusEnum {
	
	OFFRIDING(0), ONRIDING(1);
	
	int ridingStatusId;
	
	private RidingStatusEnum(int ridingStatusId) {
		this.ridingStatusId = ridingStatusId;
	}
	
	public int getRidingStatusId() {
		return ridingStatusId;
	}
}
