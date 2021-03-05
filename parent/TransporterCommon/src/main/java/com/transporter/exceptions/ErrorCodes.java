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
	SOMEVALUES("Some Values are missing"),
	VEHICLENOTFOUND("Vehicle not exit"),
	VEHICLEIDNOTFOUND("Vehicle Id not exit"),
	VSAVE("Vehicle Details  not saved"),
	CNFOUND("Customer not found, please register"),
	INVALIDRATING("Rating can not be zero"),
	TRIPDETAILSNOTFOUND("Trip details not found"),
	TRIPIDNOTFOUND("Trip ID not found"),
	SUCCESS("success"),
	DRIVERPUSHNOTIFICATIONERRORWHILEBOOKING("Push notification error for driver, while booking"),
	FCMTOKEN("FCM Token Missing"),
	TRIPDETAILSNOTSAVED("Trip details are not saved"),
	DRIVERNOTFOUND("Driver not found"),
	DRIVER_NOT_REACHED_LOCATION("driver not reached location"),
	IN_VALID_LOCATION_TYPE("invalid location type");
	
	
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
