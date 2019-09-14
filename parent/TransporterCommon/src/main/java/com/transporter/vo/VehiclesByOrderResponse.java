package com.transporter.vo;

public class VehiclesByOrderResponse {
	private int vehicleType;
	private String vehicleName;
	private double capacity;
	private double price;
	private String vehicleSelectedUrl;
	private String vehicleUnSelecetedUrl;
	public int getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	public final String getVehicleName() {
		return vehicleName;
	}
	public final void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getVehicleSelectedUrl() {
		return vehicleSelectedUrl;
	}
	public void setVehicleSelectedUrl(String vehicleSelectedUrl) {
		this.vehicleSelectedUrl = vehicleSelectedUrl;
	}
	public String getVehicleUnSelecetedUrl() {
		return vehicleUnSelecetedUrl;
	}
	public void setVehicleUnSelecetedUrl(String vehicleUnSelecetedUrl) {
		this.vehicleUnSelecetedUrl = vehicleUnSelecetedUrl;
	}
}
