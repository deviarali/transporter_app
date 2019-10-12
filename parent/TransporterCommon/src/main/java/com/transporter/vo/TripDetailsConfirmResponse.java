package com.transporter.vo;

public class TripDetailsConfirmResponse {
	
	private int id;
	private String driverMobileNumber;	
	private String tripStartOtp;
	private String driverName;
	private String vehicleName;
	private String vehicleNumber;
	private String vehicleImage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDriverMobileNumber() {
		return driverMobileNumber;
	}
	public void setDriverMobileNumber(String driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}
	public String getTripStartOtp() {
		return tripStartOtp;
	}
	public void setTripStartOtp(String tripStartOtp) {
		this.tripStartOtp = tripStartOtp;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getVehicleImage() {
		return vehicleImage;
	}
	public void setVehicleImage(String vehicleImage) {
		this.vehicleImage = vehicleImage;
	}
		
}
