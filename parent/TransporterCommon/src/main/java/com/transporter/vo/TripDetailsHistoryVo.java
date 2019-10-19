package com.transporter.vo;

import java.io.Serializable;

public class TripDetailsHistoryVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String driverName;
	private String vehicleName;
	private String vehicleNumber;
    private int vehicleType;
	private String vehicleImage;
	private String vehicleModel;
	private String tripHours;
	private Double cgst;
	private Double sgst;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleImage() {
		return vehicleImage;
	}
	public void setVehicleImage(String vehicleImage) {
		this.vehicleImage = vehicleImage;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getTripHours() {
		return tripHours;
	}
	public void setTripHours(String tripHours) {
		this.tripHours = tripHours;
	}
	public Double getCgst() {
		return cgst;
	}
	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}
	public Double getSgst() {
		return sgst;
	}
	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}
}