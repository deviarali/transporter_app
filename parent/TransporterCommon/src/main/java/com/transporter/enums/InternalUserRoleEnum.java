package com.transporter.enums;

/**
 * @author Devappa.Arali
 *
 */

public enum InternalUserRoleEnum {
	
	ADMIN(1), EXECUTIVE(2), FIELDEXECUTIVE(3);
	
	private int internalUserRoleId;

	private InternalUserRoleEnum(int internalUserRoleId) {

		this.internalUserRoleId = internalUserRoleId;
	}

	public int getId() {
		return internalUserRoleId;
	}
}
