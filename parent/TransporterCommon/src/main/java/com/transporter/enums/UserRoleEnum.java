package com.transporter.enums;

public enum UserRoleEnum {
	CUSTOMER(1);
	
	private int userRoleId;

	private UserRoleEnum(int userRoleId) {

		this.userRoleId = userRoleId;
	}

	public int getId() {
		return userRoleId;
	}

}
