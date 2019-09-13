package com.transporter.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the vehicledetails database table.
 * 
 */
public class VehicleDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private Date createdOn;

	private String vehicleColor;

	private String vehicleDocuments;

	private String vehicleModel;

	private String vehicleNum;

	private VehicleTypeVo vehicleTypeVo;

	private String vehicleVerificationPendingReason;

	private String vehicleVerificationStatus;

	private String createdBy;

	private DriverDetailsVo driverDetails;

	private String verifiedBy;

	public VehicleDetailsVo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleDocuments() {
		return vehicleDocuments;
	}

	public void setVehicleDocuments(String vehicleDocuments) {
		this.vehicleDocuments = vehicleDocuments;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public VehicleTypeVo getVehicleTypeVo() {
		return vehicleTypeVo;
	}

	public void setVehicleType(VehicleTypeVo vehicleTypeVo) {
		this.vehicleTypeVo = vehicleTypeVo;
	}

	public String getVehicleVerificationPendingReason() {
		return vehicleVerificationPendingReason;
	}

	public void setVehicleVerificationPendingReason(String vehicleVerificationPendingReason) {
		this.vehicleVerificationPendingReason = vehicleVerificationPendingReason;
	}

	public String getVehicleVerificationStatus() {
		return vehicleVerificationStatus;
	}

	public void setVehicleVerificationStatus(String vehicleVerificationStatus) {
		this.vehicleVerificationStatus = vehicleVerificationStatus;
	}

	public DriverDetailsVo getDriverDetails() {
		return driverDetails;
	}

	public void setDriverDetails(DriverDetailsVo driverDetails) {
		this.driverDetails = driverDetails;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

}