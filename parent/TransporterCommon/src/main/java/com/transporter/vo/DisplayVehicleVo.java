package com.transporter.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DisplayVehicleVo {

	private int id;

	private int capacity;

	private int size;

	private String createdBy;

	private String selectedVehicleUrl;

	private int height;

	private int width;

	private int length;

	private String unselectedVehicleUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
	
	

}