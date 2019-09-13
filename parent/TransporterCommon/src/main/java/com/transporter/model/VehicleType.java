package com.transporter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.transporter.vo.VehicleTypeVo;

@Entity
@Table(name = "vehicletype")
public class VehicleType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "size")
	private int size;

	@Column(name = "vehicle_name")
	private String vehicleName;

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

	@Column(name = "price")
	private Double price;

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

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public static VehicleTypeVo convertModelToVo(VehicleType vehicleType) {
		if(vehicleType == null)
			return null;
		VehicleTypeVo vehicleTypeVo = new VehicleTypeVo();
		vehicleTypeVo.setId(vehicleType.getId());
		vehicleTypeVo.setCapacity(vehicleType.getCapacity());
		vehicleTypeVo.setSize(vehicleType.getSize());
		vehicleTypeVo.setVehicleName(vehicleType.getVehicleName());
		vehicleTypeVo.setCreatedBy(vehicleType.getCreatedBy());
		vehicleTypeVo.setHeight(vehicleType.getHeight());
		vehicleTypeVo.setLength(vehicleType.getLength());
		vehicleTypeVo.setSelectedVehicleUrl(vehicleType.getSelectedVehicleUrl());
		vehicleTypeVo.setWidth(vehicleType.getWidth());
		vehicleTypeVo.setPrice(vehicleType.getPrice());
		return vehicleTypeVo;
	}
}