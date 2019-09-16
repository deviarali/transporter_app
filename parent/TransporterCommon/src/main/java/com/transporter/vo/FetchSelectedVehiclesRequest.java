package com.transporter.vo;

public class FetchSelectedVehiclesRequest {
	private int vehicleType;
	private double lattitude;
	private double longitude;
	private double surroudingDistance;
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
	public final double getSurroudingDistance() {
		return surroudingDistance;
	}
	public final void setSurroudingDistance(double surroudingDistance) {
		this.surroudingDistance = surroudingDistance;
	}
}
