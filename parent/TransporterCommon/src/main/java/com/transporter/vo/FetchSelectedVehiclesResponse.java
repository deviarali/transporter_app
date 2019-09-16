package com.transporter.vo;

public class FetchSelectedVehiclesResponse {
	private int vehicleType;
	private double lattitude;
	private double longitude;
	private int vehicleId;
	private int driverId;
	public final int getVehicleType() {
		return vehicleType;
	}
	public final void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	public final double getLattitude() {
		return lattitude;
	}
	public final void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public final double getLongitude() {
		return longitude;
	}
	public final void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public final int getVehicleId() {
		return vehicleId;
	}
	public final void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public final int getDriverId() {
		return driverId;
	}
	public final void setDriverId(int driverId) {
		this.driverId = driverId;
	}
}
