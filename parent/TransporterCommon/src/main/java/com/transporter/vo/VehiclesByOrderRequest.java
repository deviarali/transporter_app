package com.transporter.vo;

public class VehiclesByOrderRequest {
	
	private String lattitude;
	private String longitude;
	private double distance;
	private double capacity;
	private double surroundingDistances;
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getSurroundingDistances() {
		return surroundingDistances;
	}
	public void setSurroundingDistances(double surroundingDistances) {
		this.surroundingDistances = surroundingDistances;
	}
	
}
