package com.transporter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "displayvehicle")
public class DisplayVehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "capacity")
	private int capacity;
	
	@Column(name = "size")
	private int size;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "selected_vehicle_url")
	private String selectedVehicleUrl;
	
	@Column(name = "height")
	private int height;
	
	@Column(name = "width")
	private int width;
	
	@Column(name = "length")
	private int length;
	
	@Column(name = "unselected_vehicle_url")
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
