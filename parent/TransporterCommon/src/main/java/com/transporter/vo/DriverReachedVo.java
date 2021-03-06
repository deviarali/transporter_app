package com.transporter.vo;

import com.transporter.enums.LocationType;

public class DriverReachedVo {
	double locationLatitude;
	double locationLongitude;
	double driverLocationLatitude;
	double driverLocationLongitude;
	int tripId;
	String locationType; //LocationType
	public double getLocationLatitude() {
		return locationLatitude;
	}
	public void setLocationLatitude(double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}
	public double getLocationLongitude() {
		return locationLongitude;
	}
	public void setLocationLongitude(double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}
	public double getDriverLocationLatitude() {
		return driverLocationLatitude;
	}
	public void setDriverLocationLatitude(double driverLocationLatitude) {
		this.driverLocationLatitude = driverLocationLatitude;
	}
	public double getDriverLocationLongitude() {
		return driverLocationLongitude;
	}
	public void setDriverLocationLongitude(double driverLocationLongitude) {
		this.driverLocationLongitude = driverLocationLongitude;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	
	
}
