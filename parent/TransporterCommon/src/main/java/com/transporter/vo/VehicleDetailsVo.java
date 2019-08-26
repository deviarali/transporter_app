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

	private String vehicleType;

	private String vehicleVerificationPendingReason;

	private String vehicleVerificationStatus;

	private UserVo createdByVo;

	private DriverDetailsVo driverDetailsVo;

	private UserVo verifiedByVo;

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
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

	public UserVo getCreatedByVo() {
		return createdByVo;
	}

	public void setCreatedByVo(UserVo createdByVo) {
		this.createdByVo = createdByVo;
	}

	public DriverDetailsVo getDriverDetailsVo() {
		return driverDetailsVo;
	}

	public void setDriverDetailsVo(DriverDetailsVo driverDetailsVo) {
		this.driverDetailsVo = driverDetailsVo;
	}

	public UserVo getVerifiedByVo() {
		return verifiedByVo;
	}

	public void setVerifiedByVo(UserVo verifiedByVo) {
		this.verifiedByVo = verifiedByVo;
	}
	
}