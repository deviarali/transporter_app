package com.transporter.exceptions;

/**
 * @author Devappa.Arali
 *
 */

public enum ErrorCodes 
{
	MOEXISTS("Mobile already exists"),
	NOTSAVED("Not Saved"),
	VEHICLEEXISTS("Vehicle already exists"),
	INVALIDOTP("Invalid otp"),
	UNFOUND("User not found, please register"),
	VEHICLENOTFOUND("Vehicle not exit"),
	VSAVE("Vehicle Details  not saved");
	
	
	private final String value;

	private ErrorCodes(String value) {
		this.value = value;
	}
	
	public String value() {
        return value;
    }

    public static ErrorCodes fromValue(String value) {
        for (ErrorCodes appCode : ErrorCodes.values()) {
            if (appCode.value.equals(value)) {
                return appCode;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
