package com.transporter.vo;

import org.springframework.web.multipart.MultipartFile;

public class VehicleTypeVo {

	private int id;

	private double capacity;

	private int size;
	
	private String vehicleName;
	
	private Double price;

	private String createdBy;

	private String selectedVehicleUrl;

	private int height;

	private int width;

	private int length;

	private String unselectedVehicleUrl;
	
	private MultipartFile selectedVehicle;
	
	private MultipartFile unSelectedVehicle;
	
	private Double perKm;
	
	private Double minKm;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSelectedVehicleUrl() {
		return selectedVehicleUrl;
	}

	public void setSelectedVehicleUrl(String selectedVehicleUrl) {
		this.selectedVehicleUrl = selectedVehicleUrl;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getUnselectedVehicleUrl() {
		return unselectedVehicleUrl;
	}

	public void setUnselectedVehicleUrl(String unselectedVehicleUrl) {
		this.unselectedVehicleUrl = unselectedVehicleUrl;
	}

	public MultipartFile getSelectedVehicle() {
		return selectedVehicle;
	}

	public void setSelectedVehicle(MultipartFile selectedVehicle) {
		this.selectedVehicle = selectedVehicle;
	}

	public MultipartFile getUnSelectedVehicle() {
		return unSelectedVehicle;
	}

	public void setUnSelectedVehicle(MultipartFile unSelectedVehicle) {
		this.unSelectedVehicle = unSelectedVehicle;
	}

	public double getPerKm() {
		return perKm;
	}

	public void setPerKm(Double perKm) {
		this.perKm = perKm;
	}

	public Double getMinKm() {
		return minKm;
	}

	public void setMinKm(Double minKm) {
		this.minKm = minKm;
	}
	
}
